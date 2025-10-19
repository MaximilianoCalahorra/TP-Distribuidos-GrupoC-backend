package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import proto.services.kafka.PublicacionTransferenciaDonacionKafkaProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Clients.KafkaServiceClient;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearInventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ItemDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.TransferenciaDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Categoria;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.NombreRol;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Operacion;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.InventarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.ItemDonacionMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.TransferenciaDonacionMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Inventario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.SolicitudDonacion;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.TransferenciaDonacion;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IInventarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.ISolicitudDonacionRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.ITransferenciaDonacionRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IUsuarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.ITransferenciaDonacionService;

@Service
@RequiredArgsConstructor
public class TransferenciaDonacionService implements ITransferenciaDonacionService {
	///Atributos:
	private final ITransferenciaDonacionRepository transferenciaDonacionRepository;
	private final IInventarioRepository inventarioRepository;
	private final IUsuarioRepository usuarioRepository;
	private final ISolicitudDonacionRepository solicitudDonacionRepository;
    private final KafkaServiceClient kafkaServiceClient;
	@Value("${ong.id}")
    private String ongEmpujeComunitarioId;
	
	///Transferencias de nuestra ONG:
	@Override
    public List<TransferenciaDonacionDTO> listarTransferencias() {
		List<TransferenciaDonacion> transferencias = transferenciaDonacionRepository.findAll();
        return transferencias.stream()
                         .map(TransferenciaDonacionMapper::aDTO) 
                         .collect(Collectors.toList());
	}
    
    ///Transferencias salientes de nuestra ONG:
	@Override
    public List<TransferenciaDonacionDTO> listarTransferenciasSalientes(String idOrganizacionDonante) {
		List<TransferenciaDonacion> transferencias = transferenciaDonacionRepository.findByIdOrganizacionDonante(ongEmpujeComunitarioId);
		return transferencias.stream()
                         .map(TransferenciaDonacionMapper::aDTO) 
                         .collect(Collectors.toList());
	}
    
    ///Transferencias entrantes de nuestra ONG:
	@Override
	public List<TransferenciaDonacionDTO> listarTransferenciasEntrantes(String idOrganizacionReceptora) {
		List<TransferenciaDonacion> transferencias = transferenciaDonacionRepository.findByIdOrganizacionReceptora(ongEmpujeComunitarioId);
		return transferencias.stream()
                         .map(TransferenciaDonacionMapper::aDTO) 
                         .collect(Collectors.toList());
	}
    
    ///Crear transferencia saliente:
	@Override
	@Transactional
	public void crearTransferenciaSaliente(TransferenciaDonacionDTO transferencia) {
		//Buscar la solicitud de donación:
        Optional<SolicitudDonacion> solicitudOpt = solicitudDonacionRepository.findByIdSolicitudDonacionOrigenAndIdOrganizacion(transferencia.getIdSolicitudDonacion(), transferencia.getIdOrganizacionReceptora());

        //Si la solicitud no existe:
        if (!solicitudOpt.isPresent()) {
            throw new EntityExistsException("La solicitud de donación no existe.");
        }
        
        //Obtenemos el usuario autenticado
  		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  		UserDetails userData = (UserDetails) auth.getPrincipal();
  		Optional<Usuario> usuarioAutenticado = usuarioRepository.findByNombreUsuario(userData.getUsername());
  		
  		//Si no hay un usuario autenticado:
        if (!usuarioAutenticado.isPresent()) {
            throw new EntityNotFoundException("No hay un usuario autenticado.");
        }
        
        Usuario usuario = usuarioAutenticado.get(); //Accedemos al usuario autenticado.
        
        //Variables auxiliares:
        int i = 0;
        List<ItemDonacionDTO> itemsTransferencia = transferencia.getItems();
        LocalDateTime ahora = LocalDateTime.now();
        
        //Analizamos cada ítem a transferir:
        while (i < transferencia.getItems().size()) {
        	
        	ItemDonacionDTO item = itemsTransferencia.get(i); //Obtenemos el ítem.
        	
        	//Buscamos si hay un inventario para esa combinación de categoría y descripción:
			Optional<Inventario> inventarioOpt = inventarioRepository.findByCategoriaAndDescripcion(item.getCategoria(), item.getDescripcion());
			
			//Si no hay un inventario...
			if (!inventarioOpt.isPresent()) {
				throw new IllegalArgumentException("No tenemos un inventario " + item.getCategoria() + " - " + item.getDescripcion() +  "."); //Mensaje informativo.
			}
			
			Inventario inventario = inventarioOpt.get(); //Obtenemos el inventario.
			
			//Si no tenemos suficiente stock...
			if (inventario.getCantidad() < item.getCantidad()) {
				throw new IllegalArgumentException("No tenemos suficiente stock de " + item.getCategoria() + " - " + item.getDescripcion() +  "."); //Mensaje informativo.
			}
			
			//Si tenemos stock, modificamos el inventario y el ítem y persistimos:
			item.setOperacion(Operacion.TRANSFERENCIA);
			inventario.setCantidad(inventario.getCantidad() - item.getCantidad());
			inventario.setFechaHoraModificacion(ahora);
			inventario.setUsuarioModificacion(usuario);
			
			inventarioRepository.save(inventario);
			
			i++; //Pasamos al siguiente ítem.
        }
        
        //Si la solicitud existe y tenemos stock para transferir, cargamos los demás datos de la transferencia:
		transferencia.setFechaHora(LocalDateTime.now());
		transferencia.setIdOrganizacionDonante(ongEmpujeComunitarioId);
		
		//Persistimos la transferencia:
		TransferenciaDonacion transferenciaEntidad = TransferenciaDonacionMapper.aEntidad(transferencia);
		transferenciaDonacionRepository.save(transferenciaEntidad);
		
		//Intentar publicar en Kafka la transferencia:
        try {
        	//Armar primera parte del mensaje:
            PublicacionTransferenciaDonacionKafkaProto.Builder protoBuilder =
                PublicacionTransferenciaDonacionKafkaProto.newBuilder()
                	.setIdOrganizacionDonante(ongEmpujeComunitarioId)
                	.setIdOrganizacionReceptora(transferencia.getIdOrganizacionReceptora())
                    .setIdSolicitud(transferencia.getIdSolicitudDonacion());

            //Agregar ítems al mensaje:
            for (ItemDonacionDTO item : itemsTransferencia) {
                protoBuilder.addDonaciones(ItemDonacionMapper.aItemTransferenciaProto(item));
            }

            //Construir el mensaje final:
            PublicacionTransferenciaDonacionKafkaProto proto = protoBuilder.build();

            //Publicar:
            kafkaServiceClient.publicarTransferenciaDonacion(proto);
        } catch (Exception e) {
            System.out.println("Error publicando la transferencia de donación en Kafka " + e);
        }
	}
    
    ///Crear transferencia entrante:
	@Override
	@Transactional
	public void crearTransferenciaEntrante(TransferenciaDonacionDTO transferencia) {
		//Buscar la solicitud de donación:
        Optional<SolicitudDonacion> solicitudOpt = solicitudDonacionRepository.findByIdSolicitudDonacionOrigenAndIdOrganizacion(transferencia.getIdSolicitudDonacion(), ongEmpujeComunitarioId);

        //Si la solicitud no existe:
        if (!solicitudOpt.isPresent()) {
            throw new EntityExistsException("La solicitud de donación no existe.");
        }
        
		//Si la solicitud existe, cargamos los demás datos de la transferencia:
		transferencia.setFechaHora(LocalDateTime.now());
		transferencia.setIdOrganizacionReceptora(ongEmpujeComunitarioId);
		
		//Recorremos los ítems de la transferencia:
		for (ItemDonacionDTO item: transferencia.getItems()) {
			item.setOperacion(Operacion.TRANSFERENCIA); //Definimos en qué tipo de operación está inmerso el ítem.
			
			//Buscamos si hay un inventario para esa combinación de categoría y descripción:
			Optional<Inventario> inventarioOpt = inventarioRepository.findByCategoriaAndDescripcion(item.getCategoria(), item.getDescripcion());
			
			//Si hay un inventario...
			if (inventarioOpt.isPresent()) {
				Inventario inventario = inventarioOpt.get(); //Obtenemos el inventario.
				
				//Obtenemos al presidente:
				Usuario presidente = usuarioRepository.findByRol_NombreRol(NombreRol.PRESIDENTE)
				        .orElseThrow(() -> new IllegalStateException("No se encontró el usuario PRESIDENTE"));

				//Obtenemos la fecha y hora actual:
			    LocalDateTime ahora = LocalDateTime.now();
				
			    //Cargamos los datos del inventario:
			    inventario.setCantidad(inventario.getCantidad() + item.getCantidad());
				inventario.setFechaHoraModificacion(ahora);
				inventario.setUsuarioModificacion(presidente);

				inventarioRepository.save(inventario); //Persistimos la acumulación de cantidad.
			} else {
				//Si no hay un inventario, lo creamos con los datos de la transferencia:
				CrearInventarioDTO inventarioNuevo = new CrearInventarioDTO(item.getCategoria(), item.getDescripcion(), item.getCantidad());
				crearInventario(inventarioNuevo);
			}
		}
		
		//Persistimos la transferencia:
		TransferenciaDonacion transferenciaEntidad = TransferenciaDonacionMapper.aEntidad(transferencia);
		transferenciaDonacionRepository.save(transferenciaEntidad);
	}
	
	@Transactional
	private void crearInventario(CrearInventarioDTO dto) {
		//Validamos la categoría:
		if (dto.getCategoria() == null || dto.getCategoria() == Categoria.DESCONOCIDA) {
	        throw new IllegalArgumentException("La categoría es obligatoria.");
	    }
	    
		//Validamos la descripción:
	    if (dto.getDescripcion() == null || dto.getDescripcion().isBlank()) {
	        throw new IllegalArgumentException("La descripción es obligatoria.");
	    }
	    
	    //Validamos la cantidad:
	    if (dto.getCantidad() < 0) {
	        throw new IllegalArgumentException("La cantidad no puede ser negativa.");
	    }
	
	    //Obtenemos al presidente:
		Usuario presidente = usuarioRepository.findByRol_NombreRol(NombreRol.PRESIDENTE)
		        .orElseThrow(() -> new IllegalStateException("No se encontró el usuario PRESIDENTE"));

		//Obtenemos la fecha y hora actual:
	    LocalDateTime ahora = LocalDateTime.now();
		
	    //Creamos el inventario:
		Inventario inventario = InventarioMapper.aEntidad(dto);
		inventario.setEliminado(false);
		inventario.setFechaHoraAlta(ahora);
		inventario.setFechaHoraModificacion(ahora);
		inventario.setUsuarioAlta(presidente);
		inventario.setUsuarioModificacion(presidente);

		//Persistimos el inventario:
	    inventarioRepository.save(inventario);
	}
}

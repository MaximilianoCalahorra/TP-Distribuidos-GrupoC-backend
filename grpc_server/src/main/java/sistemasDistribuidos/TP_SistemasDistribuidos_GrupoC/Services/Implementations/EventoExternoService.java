package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoExterno;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IEventoExternoRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IEventoExternoService;

@Service
@RequiredArgsConstructor
public class EventoExternoService implements IEventoExternoService {
	///Atributos
    private final IEventoExternoRepository eventoExternoRepository;
    
    ///Eliminar evento:
    @Override
    @Transactional
    public void eliminarEventoExterno(Long idEventoExterno) {
    	//Buscar evento por id:
    	Optional<EventoExterno> eventoOpt = eventoExternoRepository.findById(idEventoExterno);
    	
    	//Si el evento no existe:
        if (!eventoOpt.isPresent()) {
            throw new EntityNotFoundException("Evento solidario no encontrado.");
        }

        //Obtener el evento:
        EventoExterno evento = eventoOpt.get();
        
        //Eliminar relación con los participantes:
        evento.getParticipantesInternos().clear();
        eventoExternoRepository.save(evento);

        //Eliminar el evento:
        eventoExternoRepository.delete(evento);
    }
}

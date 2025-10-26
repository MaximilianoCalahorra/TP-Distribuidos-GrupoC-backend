package org.empuje_comunitario.rest_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.empuje_comunitario.rest_service.service.ReporteDonacionesService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Tag(name = "Reportes")
@RestController
@RequestMapping("/api/reportes")
public class ReporteDonacionesController {

    private final ReporteDonacionesService service;

    public ReporteDonacionesController(ReporteDonacionesService service) { this.service = service; }

    @Operation(
            summary = "Descargar informe de donaciones en Excel",
            description = "Genera un archivo .xlsx agrupado por categoría con el detalle de donaciones."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Archivo Excel generado correctamente",
            content = @Content(
                    mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    schema = @Schema(type = "string", format = "binary")
            )
    )
    @ApiResponse(responseCode = "500", description = "Error generando el Excel")
    @GetMapping(value = "/donaciones.xlsx",
            produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> descargarExcel() {
        byte[] excel = service.generarExcelCompleto();
        String filename = "donaciones_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm")) + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .cacheControl(CacheControl.noCache())
                .body(excel);
    }
}
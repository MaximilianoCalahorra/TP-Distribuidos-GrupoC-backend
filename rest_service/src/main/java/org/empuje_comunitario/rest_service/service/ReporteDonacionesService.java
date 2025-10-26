package org.empuje_comunitario.rest_service.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.empuje_comunitario.rest_service.repository.IInformeDonacionViewRepository;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReporteDonacionesService {
    private final IInformeDonacionViewRepository repo;

    public ReporteDonacionesService(IInformeDonacionViewRepository repo) {
        this.repo = repo;
    }

    public byte[] generarExcelCompleto() {
        var rows = repo.findAllOrdered();

        var porCategoria = rows.stream().collect(Collectors.groupingBy(
                v -> v.getCategoria() == null ? "SIN_CATEGORIA" : v.getCategoria().name(),
                LinkedHashMap::new, Collectors.toList()
        ));

        try (var wb = new SXSSFWorkbook(200); var baos = new ByteArrayOutputStream()) {
            var header = headerStyle(wb);
            var dateOnly = dateStyle(wb);

            if (porCategoria.isEmpty()) {
                var s = wb.createSheet("SIN_DATOS");
                s.createRow(0).createCell(0).setCellValue("No hay registros en vw_informe_donaciones.");
            } else {
                for (var entry : porCategoria.entrySet()) {
                    var sheet = wb.createSheet(sanitize(entry.getKey()));

                    // Columnas base de tu consigna:
                    var cols = new String[]{
                            "Fecha de Alta", "Descripcion", "Cantidad", "Eliminado",
                            "Usuario Alta", "Usuario Modificación", "Rol Usuario Alta",
                            "Rol Usuario Modificación", "Org Donante", "Org Receptora",
                            "Operacion"
                    };

                    var h = sheet.createRow(0);
                    for (int i = 0; i < cols.length; i++) {
                        var c = h.createCell(i);
                        c.setCellValue(cols[i]);
                        c.setCellStyle(header);
                    }

                    int r = 1;
                    for (var v : entry.getValue()) {
                        var row = sheet.createRow(r++);
                        // Fecha de Alta (solo fecha)
                        var c0 = row.createCell(0);
                        if (v.getFechaAlta() != null) {
                            c0.setCellValue(java.sql.Date.valueOf(v.getFechaAlta()));
                            c0.setCellStyle(dateOnly);
                        } else {
                            c0.setBlank();
                        }

                        row.createCell(1).setCellValue(nz(v.getDescripcion()));
                        row.createCell(2).setCellValue(v.getCantidad() == null ? 0 : v.getCantidad());
                        row.createCell(3).setCellValue(Boolean.TRUE.equals(v.getEliminado()) ? "Sí" : "No");
                        row.createCell(4).setCellValue(nz(v.getUsuarioAlta()));
                        row.createCell(5).setCellValue(nz(v.getUsuarioModificacion()));
                        row.createCell(6).setCellValue(nz(v.getRolUsuarioAlta()));
                        row.createCell(7).setCellValue(nz(v.getRolUsuarioModificacion()));
                        row.createCell(8).setCellValue(nz(v.getOrgDonante()));
                        row.createCell(9).setCellValue(nz(v.getOrgReceptora()));
                        row.createCell(10).setCellValue(v.getOperacion() == null ? "" : v.getOperacion().name());
                    }

                    for (int i = 0; i < cols.length; i++) safeAutosize(sheet, i);
                }
            }

            wb.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generando Excel", e);
        }
    }

    private static String nz(String s) { return s == null ? "" : s; }
    private static String sanitize(String n) { return (n == null || n.isBlank()) ? "SIN_CATEGORIA" : n.replaceAll("[:\\\\/?*\\[\\]]", "_").trim(); }
    private static void safeAutosize(Sheet s, int c) { try { s.autoSizeColumn(c); } catch (Exception ignored) {} }
    private static CellStyle headerStyle(Workbook wb) { var st = wb.createCellStyle(); var f = wb.createFont(); f.setBold(true); st.setFont(f); return st; }
    private static CellStyle dateStyle(Workbook wb) { var st = wb.createCellStyle(); st.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat("yyyy-mm-dd")); return st; }
}

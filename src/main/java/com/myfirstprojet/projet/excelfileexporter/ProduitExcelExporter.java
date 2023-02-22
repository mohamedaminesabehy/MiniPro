package com.myfirstprojet.projet.excelfileexporter;
import org.apache.poi.ss.usermodel.Font;
import com.myfirstprojet.projet.entity.Produit;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public  class ProduitExcelExporter {

    private ProduitExcelExporter() {
    }

    public static ByteArrayInputStream produitsToExcel(List<Produit> produits) throws IOException {
        String[] columns = {"id", "nom", "qt", "Disponible", "datecreation", "datemodification", "Categorie id"};
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            Sheet sheet = workbook.createSheet("Produits");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (Produit produit : produits) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(produit.getId());
                row.createCell(1).setCellValue(produit.getNom());
                row.createCell(2).setCellValue(produit.getQt());
                row.createCell(3).setCellValue(produit.getDisponible());
                row.createCell(4).setCellValue(produit.getDatecreation().toString());
                row.createCell(5).setCellValue(produit.getDatemodification().toString());

                if (produit.getCategorieid() != null) {
                    row.createCell(6).setCellValue(produit.getCategorieid().getId());
                } else {
                    row.createCell(6).setCellValue("");
                }
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }



}

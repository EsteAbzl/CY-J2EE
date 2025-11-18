package com.servlet;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;

@WebServlet("/ExportPayslipServlet")
public class ExportPayslipServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupération des paramètres envoyés par le formulaire caché
        String employee = request.getParameter("employee");
        String month = request.getParameter("month");
        int year = Integer.parseInt(request.getParameter("year"));
        double baseSalary = Double.parseDouble(request.getParameter("baseSalary"));
        double bonus = Double.parseDouble(request.getParameter("bonus"));
        double deductions = Double.parseDouble(request.getParameter("deductions"));
        double netSalary = Double.parseDouble(request.getParameter("netSalary"));

        // Configuration de la réponse HTTP pour un PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=ficheDePaie_"+employee+"_"+month+"_"+year+".pdf");

        // Création du PDF avec iText
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Titre
        document.add(new Paragraph("Fiche de paie")
                .setBold()
                .setFontSize(18)
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER));

        document.add(new Paragraph("Employé : " + employee));
        document.add(new Paragraph("Période : " + month + " " + year));
        document.add(new Paragraph("\n"));

        // Tableau formaté
        Table table = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
        table.setWidth(UnitValue.createPercentValue(100));

        table.addCell(new Cell().add(new Paragraph("Salaire de base")).setBold());
        table.addCell(new Cell().add(new Paragraph(baseSalary + " €")));

        table.addCell(new Cell().add(new Paragraph("Prime")).setBold());
        table.addCell(new Cell().add(new Paragraph(bonus + " €")));

        table.addCell(new Cell().add(new Paragraph("Déductions")).setBold());
        table.addCell(new Cell().add(new Paragraph(deductions + " €")));

        table.addCell(new Cell().add(new Paragraph("Salaire net")).setBold());
        table.addCell(new Cell().add(new Paragraph(netSalary + " €")).setBold());

        document.add(table);

        document.close();
    }
}

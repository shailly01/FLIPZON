package com.my.spring.pojo;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.my.spring.dao.CartDAO;

public class PDFView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document pdfdoc, PdfWriter pdfwriter, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		Font helvetica_18_blue = new Font(Font.HELVETICA, 18, Font.BOLDITALIC, Color.BLUE);

		List<Cart> cartlist = (List<Cart> ) model.get("cartitems");
		Paragraph title = new Paragraph("Thank you for shopping at Flipzon Store", helvetica_18_blue);

		Phrase producttitle = new Phrase("Below you will find a summary of the item you have purchased");
		float total = 0.0f;
		for (Cart c: cartlist) {
			total += c.getQuantity() * c.getTotalprice();
			//total += c.getTotalprice();
		}

		Paragraph Space1 = new Paragraph();
		Paragraph Space2 = new Paragraph();
		Paragraph Space3 = new Paragraph();

		Paragraph TotalPrice = new Paragraph("Total Price " + total);

		Phrase thank = new Phrase("Thank you for shopping with us");

		PdfPTable table = new PdfPTable(3); ///change from 3 to 2
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {
			3.0f, 2.0f, 1.0f
		});
		//table.setWidths(new float[] {3.0f, 2.0f});
		table.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.WHITE);
		cell.setPadding(5);

		cell.setPhrase(new Phrase("Product Title", helvetica_18_blue));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Quantity", helvetica_18_blue));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Price", helvetica_18_blue));
		table.addCell(cell);

		for (Cart ccc: cartlist) {
			table.addCell(ccc.getTitle());
			table.addCell(String.valueOf(ccc.getQuantity()));
			table.addCell(String.valueOf(ccc.getTotalprice()));
		}

		pdfdoc.add(title);
		pdfdoc.add(producttitle);
		pdfdoc.add(table);
		pdfdoc.add(Space1);
		pdfdoc.add(Space2);
		pdfdoc.add(Space3);

		pdfdoc.add(TotalPrice);
		pdfdoc.add(thank);

	}

}
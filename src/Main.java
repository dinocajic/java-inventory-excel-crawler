import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dino Cajic
 */

public class Main {

    public static void main(String[] args) {

        try( Workbook wb = WorkbookFactory.create(
                new File("C:/Full_path/inventory.xls") )
        ) {
            // Create Sheet
            Sheet sheet = wb.getSheetAt(0);

            // Iterate through rows
            int rowStart = 0;
            int rowEnd   = sheet.getLastRowNum();

            // Initialize location
            String location  = "location1";
            List<Item> items = new ArrayList<>();

            for( int i = rowStart; i < rowEnd; i++ ) {
                Row row = sheet.getRow(i);

                // If blank values: throws NullPointerException
                if (row.getCell(0) == null || row.getCell(12) == null)
                    continue;

                // We need the Cell value to be a number
                if (row.getCell(12).getCellType().equals( CellType.STRING ))
                    continue;

                // Get item number. Certain item numbers are completely numeric
                // so we need to test for that.
                String itemNumber = getItemNumber( row );
                int available_qty = (int) row.getCell(12).getNumericCellValue();

                location = setLocation( location, itemNumber );

                items.add(new Item(location, itemNumber, available_qty));
            }

            generateCSVfile( items );

        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the current location since excel sheet is subdivided into 3 sections
     *
     * @param location
     * @param itemNumber
     * @return String
     */
    private static String setLocation( String location, String itemNumber ) {

        if (itemNumber.equals("1 Location")) {
            location = "location1";
        } else if (itemNumber.equals("2 Location")) {
            location = "location2";
        } else if (itemNumber.equals("3 Location")) {
            location = "location3";
        }

        return location;
    }

    /**
     * Returns the item number
     * @param row
     * @return String
     */
    private static String getItemNumber( Row row ) {
        String itemNumber;

        if (row.getCell(0).getCellType().equals( CellType.STRING )) {
            itemNumber = row.getCell(0).getStringCellValue();
        } else {
            Object item = row.getCell(0).getNumericCellValue();
            itemNumber = new BigDecimal( item.toString() ).toPlainString();
        }

        return itemNumber;
    }

    /**
     * Cycles through the list and creates the inventory file
     * @param items
     */
    private static void generateCSVfile( List<Item> items ) {
        try (PrintWriter writer = new PrintWriter(
                new File("inventory_" + System.currentTimeMillis() + ".csv"))
        ) {

            for ( Item item: items ) {
                writer.write( item.toString() );
                writer.write("\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

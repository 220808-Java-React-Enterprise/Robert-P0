package com.revature.exotic_jerky.services;

import com.revature.exotic_jerky.daos.ProductDAO;
import com.revature.exotic_jerky.models.Product;
import com.revature.exotic_jerky.utils.custom_exceptions.InvalidFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ProductService {
    private final ProductDAO productDAO;

    // Pre:
    // Post:
    // Purpose:
    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public Map<String, Product> getProduct(String category){
        return productDAO.getAllByCategory(category);
    }

    public void update(Product product){
        productDAO.update(product);
    }

    public boolean writeInventory(String file, String storeID){
        Set<Product> productSet = new HashSet<>();

        // Define a product and it's variables
        Object[] input = new Object[7];

        // Local variables for verification
        int inCount;
        boolean keyValues = true;

        try {
            File path = new File("S:\\WorkFiles\\Git\\Revature\\Robert-P0\\src\\main\\resources\\" + file + ".xlsx");
            if (path.exists()){
                FileInputStream fis = new FileInputStream(path);

                XSSFWorkbook wb = new XSSFWorkbook(fis);

                XSSFSheet sheet = wb.getSheetAt(0);

                for (Row row : sheet){
                    Iterator<Cell> cellIterator = row.cellIterator();
                    inCount = 0;
                    while(cellIterator.hasNext()){
                        Cell cell = cellIterator.next();

                        switch(cell.getCellType()){
                            case STRING:
                                if (keyValues) break;
                                input[inCount] = cell.getStringCellValue(); break;
                            case NUMERIC:
                                input[inCount] = cell.getNumericCellValue(); break;
                        }
                        inCount++;
                    }
                    if (inCount < input.length){
                        input[inCount] = UUID.randomUUID().toString();
                    }
                    if (!keyValues)
                        productSet.add(new Product(String.valueOf(input[6]), String.valueOf(input[0]), String.valueOf(input[1]),
                            String.valueOf(input[2]), storeID, Float.parseFloat(String.valueOf(input[3])), (byte)Float.parseFloat(String.valueOf(input[4]))));
                    keyValues = false;
                }
            }
            else { System.out.println("\nFile doesn't exist. Try Again!"); return false; }
        } catch (IOException e){
            throw new InvalidFileException("Error reading file");
        }

        // Loop to update Inventory
        for (Product product : productSet){
            Product oldProduct;
            if ((oldProduct = productDAO.getProductById(product.getId())) != null){
                oldProduct.setCategory(product.getCategory());
                oldProduct.setName(product.getName());
                oldProduct.setDescription(product.getDescription());
                oldProduct.setPrice(product.getPrice());
                oldProduct.setQuantity(product.getQuantity());

                productDAO.update(oldProduct);
            }
            else
                productDAO.save(product);
        }
        return true;
    }

    public static void readInventory(){

    }
}

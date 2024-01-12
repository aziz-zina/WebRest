import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import java.util.List;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.api.client.config.ClientConfig;


import javax.ws.rs.core.MediaType;



import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Scanner;
public class main {
    public static void main(String[] args) {
    	
    	
    	ClientConfig clientConfig = new DefaultClientConfig();
    	clientConfig.getClasses().add(JacksonJsonProvider.class);
    	Client client = Client.create(clientConfig);
    	
        URI uri = UriBuilder.fromUri("http://localhost:8080/RestApi/").build();
        URI uriPost = UriBuilder.fromUri("http://localhost:8080/RestApi/service/create").build();
        URI uriPost2 = UriBuilder.fromUri("http://localhost:8080/RestApi/service/createCategorie").build();
        URI update =UriBuilder.fromUri("http://localhost:8080/RestApi/service/").build();
        Scanner scanner = new Scanner(System.in);

        printMenu();
        while (true) {
        	System.out.print("\nCHOOSE AN OPERATION TO EXECUTE: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewAllProducts(client, uri);
                    break;
                case 2:
                    createNewProduct(client, uriPost, uri);
                    break;
                case 3:
                    updateProduct(client, update);

                    break;
                case 4:
                    deleteProduct(client, update);
                    break;
                case 5:
                	viewAllCategories(client, uri);
                	break;
                case 6:
                	createNewCategory(client, uriPost2);
                	break;
                case 7:
                	updateCategory(client, update);
                	break;
                case 8:
                	deleteCategory(client, update);
                	break;
                case 9:
                    // Exit the program
                    System.out.println("EXITING......\nGOODBYE!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("INVALID OPTION'S CHOICE.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("----------------------------------OPTION MENU:----------------------------------------");
        System.out.println("------------------------------PRODUCT OPTION MENU:------------------------------------");
        System.out.println("1. VIEW ALL PRODUCTS");
        System.out.println("2. ADD A NEW PRODUCT");
        System.out.println("3. UPDATE A PRODUCT");
        System.out.println("4. DELETE A PRODUCT");
        System.out.println("------------------------------CATEGORY OPTION MENU:-----------------------------------");
        System.out.println("5. VIEW ALL CATEGORIES");
        System.out.println("6. CREATE A NEW CATEGORY");
        System.out.println("7. UPDATE A CATEGORY");
        System.out.println("8. DELETE A CATEGORY");
        System.out.println("9. EXIT");
        System.out.println("--------------------------------------------------------------------------------------");
    }
    
    private static void deleteProduct(Client client, URI update) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ENTER THE PRODUCT'S ID THAT YOU WANT TO DELETE: ");
        int productId = scanner.nextInt();

        // Check if product exists
        ClientResponse checkProductResponse = client.resource(update)
                .path("/getProduitById/" + productId)
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        if (checkProductResponse.getStatus() == 200) {
            // Product exists, proceed with deletion
            ClientResponse deleteProductResponse = client.resource(update)
                    .path("/deleteProduit/" + productId)
                    .delete(ClientResponse.class);

            if (deleteProductResponse.getStatus() == 204) {
                System.out.println("\nPRODUCT DELETED SUCCESSFULLY.");
            } else {
                System.out.println("ERROR DELETING PRODUCT: " + deleteProductResponse);
            }
        } else {
            System.out.println("PRODUCT WITH ID " + productId + " DOES NOT EXIST.");
        }
    }
    
    private static void deleteCategory(Client client, URI update) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ENTER THE CATEGORY'S ID THAT YOU WANT TO DELETE: ");
        int categoryId = scanner.nextInt();

        // Check if category exists
        ClientResponse checkCategoryResponse = client.resource(update)
                .path("/getCategorieById/" + categoryId)
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        if (checkCategoryResponse.getStatus() == 200) {
            // Category exists, proceed with deletion
            ClientResponse deleteCategoryResponse = client.resource(update)
                    .path("/deleteCategorie/" + categoryId)
                    .delete(ClientResponse.class);

            if (deleteCategoryResponse.getStatus() == 204) {
                System.out.println("\nCATEGORY DELETED SUCCESSFULLY.");
            } else {
                System.out.println("ERROR DELETING CATEGORY: " + deleteCategoryResponse);
            }
        } else {
            System.out.println("CATEGORY WITH ID " + categoryId + " DOES NOT EXIST.");
        }
    }

    private static void viewAllProducts(Client client, URI uri) {
        ClientResponse webResource = client.resource(uri)
                .path("service")
                .path("/getAllProduits")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        if (webResource.getStatus() == 200) {
            String jsonOutput = webResource.getEntity(String.class);

            org.codehaus.jackson.map.ObjectMapper objectMapper = new org.codehaus.jackson.map.ObjectMapper();
            try {
                List<product> productList = objectMapper.readValue(jsonOutput, new org.codehaus.jackson.type.TypeReference<List<product>>() {});
                System.out.println("\nLIST OF PRODUCTS:");
                System.out.println("--------------------");
                for (product product : productList) {
                    System.out.println("PRODUCT'S CODE: " + product.getCode());
                    System.out.println("PRODUCT'S NAME: " + product.getLib());
                    System.out.println("PRODUCT'S PRICE: " + product.getPrix());
                    System.out.println("PRODUCT'S QUANTITY: " + product.getQuantite());
                    System.out.println("CATEGORY'S CODE: " + product.getFkcategorie());
                    System.out.println("--------------------");
                }
            } catch (Exception e) {
                System.out.println("Error processing JSON: " + e.getMessage());
            }
        } else {
            System.out.println("ERROR OCCURED WHILE TRYING TO GET ALL THE PRODUCTS");
        }
    }
    
    private static void viewAllCategories(Client client, URI uri) {
        ClientResponse webResource = client.resource(uri)
                .path("service")
                .path("/getAllCategories")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        if (webResource.getStatus() == 200) {
            String jsonOutput = webResource.getEntity(String.class);

            org.codehaus.jackson.map.ObjectMapper objectMapper = new org.codehaus.jackson.map.ObjectMapper();
            try {
                List<categorie> categoriesList = objectMapper.readValue(jsonOutput, new org.codehaus.jackson.type.TypeReference<List<categorie>>() {});
                System.out.println("\nLIST OF CATEGORIES:");
                System.out.println("--------------------");
                for (categorie category : categoriesList) {
                    System.out.println("CATEGORY'S CODE: " + category.getCode());
                    System.out.println("CATEGORY'S NAME: " + category.getLib());
                    System.out.println("--------------------");
                }
            } catch (Exception e) {
                System.out.println("Error processing JSON: " + e.getMessage());
            }
        } else {
            System.out.println("ERROR OCCURED WHILE TRYING TO GET ALL THE PRODUCTS");
        }
    }
    
    private static void updateProduct(Client client, URI update) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("ENTER THE PRODUCT'S ID THAT YOU WANT TO UPDATE: ");
        int productId = scanner.nextInt();

        // Check if product exists
        ClientResponse checkProductResponse = client.resource(update)
                .path("/getProduitById/" + productId)
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        if (checkProductResponse.getStatus() == 200) {
            // Product exists, proceed with update
            product updatedProduct = new product();
            updatedProduct.setCode(productId);

            System.out.println("ENTER THE NEW PRODUCT'S NAME: ");
            updatedProduct.setLib(scanner.next());

            System.out.println("ENTER THE NEW PRODUCT'S PRICE: ");
            updatedProduct.setPrix(scanner.nextDouble());

            System.out.println("ENTER THE NEW PRODUCT'S QUANTITY: ");
            updatedProduct.setQuantite(scanner.nextInt());

            System.out.println("ENTER THE NEW PRODUCT'S CATEGORY ID: ");
            updatedProduct.setFkcategorie(scanner.nextInt());

            ClientResponse updateProductResponse = client.resource(update)
                    .path("/updateProduit")
                    .type(MediaType.APPLICATION_JSON)
                    .put(ClientResponse.class, updatedProduct);

            if (updateProductResponse.getStatus() == 204) {
                System.out.println("PRODUCT UPDATED SUCCESSFULLY.");
            } else {
                System.out.println("ERROR UPDATING PRODUCT: " + updateProductResponse);
            }
        } else {
            System.out.println("PRODUCT WITH ID " + productId + " DOES NOT EXIST.");
        }
    }
    
    private static void updateCategory(Client client, URI update) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ENTER THE CATEGORY'S ID THAT YOU WANT TO UPDATE: ");
        int categoryId = scanner.nextInt();

        // Check if category exists
        ClientResponse checkCategoryResponse = client.resource(update)
                .path("/getCategorieById/" + categoryId)
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        if (checkCategoryResponse.getStatus() == 200) {
            // Category exists, proceed with update
            categorie updatedCategory = new categorie();  // Assuming you have a categorie class
            updatedCategory.setCode(categoryId);

            System.out.print("ENTER THE NEW CATEGORY'S NAME: ");
            updatedCategory.setLib(scanner.next());

            ClientResponse updateCategoryResponse = client.resource(update)
                    .path("/updateCategorie")
                    .type(MediaType.APPLICATION_JSON)
                    .put(ClientResponse.class, updatedCategory);

            if (updateCategoryResponse.getStatus() == 204) {
                System.out.println("CATEGORY UPDATED SUCCESSFULLY.");
            } else {
                System.out.println("ERROR UPDATING CATEGORY: " + updateCategoryResponse);
            }
        } else {
            System.out.println("CATEGORY WITH ID " + categoryId + " DOES NOT EXIST.");
        }
    }

    private static void createNewProduct(Client client, URI uriPost, URI uri) {
        product newProduct = new product();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("ENTER PRODUCT'S NAME: ");
        newProduct.setLib(scanner.next());

        System.out.println("ENTER PRODUCT'S PRICE: ");
        newProduct.setPrix(scanner.nextDouble());

        System.out.println("ENTER PRODUCT'S QUANTITY: ");
        newProduct.setQuantite(scanner.nextInt());

        System.out.println("ENTER THE CATEGORY'S CODE: ");
        int categoryCode = scanner.nextInt();
        
        ClientResponse checkCategoryResponse = client.resource(uri)
                .path("service")
                .path("/getCategorieById/" + categoryCode)
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        if (checkCategoryResponse.getStatus() == 200) {
            // Category exists, proceed with product creation
            newProduct.setFkcategorie(categoryCode);
            ClientResponse createProductResponse = client.resource(uriPost + "Produit")
                    .type(MediaType.APPLICATION_JSON)
                    .post(ClientResponse.class, newProduct);
            
            newProduct.setFkcategorie(categoryCode);
            
            if (createProductResponse.getStatus() == 204) {
                System.out.println("PRODUCT ADDED SUCCESSFULLY.");
            } else {
                System.out.println("ERROR OCCURRED WHILE TRYING TO CREATE A PRODUCT");
            }
        } else {
            System.out.println("CATEGORY WITH CODE " + categoryCode + " DOES NOT EXIST.");
        }
    }
    
    private static void createNewCategory(Client client, URI uriPost2) {
    	categorie newCategory = new categorie();
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("ENTER CATEGORY'S NAME: ");
        newCategory.setLib(scanner.next());
        
        ClientResponse createCategoryResponse = client.resource(uriPost2)
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, newCategory);
    

        if (createCategoryResponse.getStatus() == 204) {
            System.out.println("CATEGORY ADDED SUCCESSFULLY.");
        } else {
            System.out.println("ERROR OCCURRED WHILE TRYING TO CREATE A PRODUCT");
            System.out.println(createCategoryResponse);
        }
    }
    
    
}
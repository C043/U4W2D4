package Mario.Fragnito;

import Mario.Fragnito.entities.Customer;
import Mario.Fragnito.entities.Order;
import Mario.Fragnito.entities.Product;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Application {
    private final static Random rand = new Random();

    public static void main(String[] args) {
        List<Product> books = new ArrayList<>();
        List<Product> babyProducts = new ArrayList<>();
        List<Product> boysProducts = new ArrayList<>();

        System.out.println("----------------Vecchi Esercizi-------------------");
        for (int i = 0; i < 10; i++) {
            Product book = new Product("Book " + i, "Books", rand.nextInt(5, 200));
            books.add(book);
        }
        for (int i = 0; i < 10; i++) {
            Product babyProduct = new Product("Baby product " + i, "Baby", rand.nextInt(5, 50));
            babyProducts.add(babyProduct);
        }
        for (int i = 0; i < 10; i++) {
            Product boysProduct = new Product("Boy product " + i, "Boys", rand.nextInt(5, 50));
            boysProducts.add(boysProduct);
        }

        Customer aldo = new Customer("Aldo Baglio", 1);
        Customer giovanni = new Customer("Giovanni Storti", 2);
        Customer giacomo = new Customer("Giacomo Poretti", 3);

        Order aldoOrder = new Order("Sent", addToCart(books, babyProducts, boysProducts), aldo);
        Order giovanniOrder = new Order("Pending", addToCart(books, babyProducts, boysProducts), giovanni);
        Order giacomoOrder = new Order("Sent", addToCart(books, books, boysProducts), giacomo);

        System.out.println(aldoOrder);
        System.out.println(giovanniOrder);
        System.out.println(giacomoOrder);

        System.out.println("Lista libri:");
        books.forEach(System.out::println);
        List<Product> expensiveBooks = books.stream().filter(book -> book.getPrice() > 100).toList();
        System.out.println("Lista libri costosi:");
        expensiveBooks.forEach(System.out::println);

        List<Order> ordersWithBaby = new ArrayList<>(Arrays.asList(aldoOrder, giacomoOrder, giovanniOrder)).stream()
                .filter(order -> order.getProducts().stream().anyMatch(product -> Objects.equals(product.getCategory(), "Baby")))
                .toList();
        ordersWithBaby.forEach(System.out::println);

        System.out.println("Lista di prodotti prima dello sconto:");
        boysProducts.forEach(System.out::println);
        System.out.println("Lista di prodotti dopo lo sconto:");
        List<Product> sale = boysProducts.stream().peek(product -> {
            double out = product.getPrice() * 10 / 100;
            product.setPrice(product.getPrice() - out);
        }).toList();
        sale.forEach(System.out::println);

        System.out.println("Ordine di un customer di tier 2");
        giovanniOrder.getProducts().forEach(System.out::println);
        System.out.println("Prodotti che sono stati ordinati da clienti tier 2 fra il primo giugno 2024 e il 30 agosto 2024:");
        List<Product> tier2OrderProducts = new ArrayList<>(Arrays.asList(aldoOrder, giacomoOrder, giovanniOrder)).stream()
                .filter(order -> order.getCustomer().getTier() == 2 && order.getOrderDate().isBefore(LocalDate.parse("2024-08-30")) && order.getOrderDate().isAfter(LocalDate.parse("2024-06-01")))
                .flatMap(order -> order.getProducts().stream())
                .toList();
        tier2OrderProducts.forEach(System.out::println);

        System.out.println("-----------------Es1-----------------");
        List<Order> totalOrders = new ArrayList<>(Arrays.asList(aldoOrder, aldoOrder, giacomoOrder, giovanniOrder));
        Map<Customer, List<Order>> ordersGroupedByCustomer = totalOrders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));
        Set<Customer> chiaviUser = ordersGroupedByCustomer.keySet();
        chiaviUser.forEach(chiave -> System.out.println(chiave + " - " + ordersGroupedByCustomer.get(chiave)));

        System.out.println("----------------Es2-------------------");
        Map<String, Double> totalCartPerCustomer = totalOrders.stream()
                .collect(Collectors.groupingBy(order -> order.getCustomer().getName(), Collectors.summingDouble(order -> order.getProducts().stream().mapToDouble(Product::getPrice).sum())));
        chiaviUser.forEach(key -> System.out.println("Totale carrello " + key + ": " + totalCartPerCustomer.get(key)));

        System.out.println("----------------Es3-------------------");
        System.out.println("Libri:");
        books.forEach(System.out::println);
        List<Product> mostExpensiveBook = books.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .limit(3)
                .toList();
        System.out.println("I tre libri più costosi:");
        mostExpensiveBook.forEach(System.out::println);

        System.out.println("----------------Es4-------------------");
        System.out.println("Costo prodotti ordini per customer:");
        totalOrders.forEach(order -> System.out.println(order.getCustomer().getName() + " - " + order.getProducts().stream().map(Product::getPrice).toList()));
        Map<String, Double> avgTotalCartPerCustomer = totalOrders.stream()
                .collect(Collectors.groupingBy(order -> order.getCustomer().getName(), Collectors.averagingDouble(order -> order.getProducts().stream().collect(Collectors.averagingDouble(Product::getPrice)))));

        System.out.println("Media ordini:");
        chiaviUser.forEach(key -> System.out.println(key + " - " + avgTotalCartPerCustomer.get(key)));

        System.out.println("----------------Es5-------------------");
        List<Product> totalProducts = new ArrayList<>(babyProducts);
        totalProducts.addAll(boysProducts);
        totalProducts.addAll(books);

        System.out.println("Tutti i prodotti:");
        totalProducts.forEach(System.out::println);

        Map<String, Double> sumCostPerCategory = totalProducts.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.summingDouble(Product::getPrice)));
        Set<String> categoryKeySet = sumCostPerCategory.keySet();
        System.out.println("Divisione dei prodotti per categoria e somma del loro costo:");
        categoryKeySet.forEach(key -> System.out.println(key + " - " + sumCostPerCategory.get(key)));

        System.out.println("----------------Es6-------------------");
/*
        salvaProdottiSulDisco(totalProducts);
*/

        System.out.println("----------------Es7-------------------");
        System.out.println(leggiProdottiDaDisco("src/productList.txt"));
    }

    public static List<Product> addToCart(List<Product> list1, List<Product> list2, List<Product> list3) {
        List<Product> shoppingCart = new ArrayList<>();
        for (int i = rand.nextInt(0, 9); i < 10; i++) {
            int randomList = rand.nextInt(1, 3);
            switch (randomList) {
                case 1 -> shoppingCart.add(list1.get(i));
                case 2 -> shoppingCart.add(list2.get(i));
                case 3 -> shoppingCart.add(list3.get(i));
            }
        }
        return shoppingCart;
    }

    public static void salvaProdottiSulDisco(List<Product> productList) {
        File file = new File("src/productList.txt");
        productList.forEach(product -> {
            try {
                FileUtils.write(file, product.getName() + "@" + product.getCategory() + "@" + product.getPrice() + "#", StandardCharsets.UTF_8, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public static ArrayList<Product> leggiProdottiDaDisco(String pathname) {
        ArrayList<Product> productList = new ArrayList<>();
        File file = new File(pathname);

        try {
            String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            String[] stringProducts = content.split("#");
            for (String prod : stringProducts) {
                String[] prodParts = prod.split("@");
                Product productGen = new Product(prodParts[0], prodParts[1], Double.parseDouble(prodParts[2]));
                productList.add(productGen);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return productList;
    }
}

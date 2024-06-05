package org.example.sec03;

import org.example.models.sec03.Book;
import org.example.models.sec03.Library;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lec05Collection {
    private static final Logger log = LoggerFactory.getLogger((Lec03Performance.class));

    public static void main(String[] args){
            var book1 = Book.newBuilder()
                    .setTitle("harry potter - part 1")
                    .setAuthor("j k rowling")
                    .setPublicationYear(1997)
                    .build();
            var book2 = book1.toBuilder().setTitle("harry potter - part 2").setPublicationYear(1998).build();
            var book3 = book1.toBuilder().setTitle("harry potter - part 1").setPublicationYear(1999).build();

            var library = Library.newBuilder()
                            .setName("Fantasy Library")
                            /*.addBooks(book1)
                            .addBooks(book2)
                            .addBooks(book3)*/
                    .addAllBooks(List.of(book1,book2,book3))
                    .build();


        var library1 = Library.newBuilder()
                .setName("Fantasy Library")
                /*.addBooks(book1)
                .addBooks(book2)
                .addBooks(book3)*/
                .addAllBooks(Set.of(book1,book2,book3))
                .build();
            log.info("{}",library1);
        log.info("{}",library);
    }
}

package com.example.library;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("library/books")
public class LibraryController {

    private LibraryService booksService = new LibraryService();

    @PostMapping("/add-book")
    public ResponseEntity<Void> addBook(@RequestBody Book book) {
        booksService.addBook(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get-sorted-books")
    public ResponseEntity<?> getAllBooksSortedByAuthorAndTitle() {
        return new ResponseEntity<>( booksService.getAllBooksSortedByAuthorAndTitle(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{title}")
    public ResponseEntity<Void> deleteBook(@PathVariable String title) {
        for (Book book : booksService.getAllBooksSortedByAuthorAndTitle()) {
            if (book.getTitle().equals(title)) {
                booksService.deleteBook(book);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<?> searchBooksByTitle(@PathVariable String title) {
        return new ResponseEntity<>(booksService.searchBooksByTitle(title), HttpStatus.OK);
    }

    @PatchMapping("/update-author/{title}/{newAuthor}")
    public ResponseEntity<Void> updateAuthor(@PathVariable String title, @PathVariable String newAuthor) {
        for (Book book : booksService.getAllBooksSortedByAuthorAndTitle()) {
            if (book.getTitle().equals(title)) {
                booksService.updateAuthor(book, newAuthor);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

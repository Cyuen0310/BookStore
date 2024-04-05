package hkmu.comps380f.BookStore.controller;

import hkmu.comps380f.BookStore.dao.AttachmentRepository;
import hkmu.comps380f.BookStore.dao.BookService;
import hkmu.comps380f.BookStore.exception.BookNotFound;
import hkmu.comps380f.BookStore.model.Book;
import hkmu.comps380f.BookStore.exception.AttachmentNotFound;
import hkmu.comps380f.BookStore.model.Attachment;
import hkmu.comps380f.BookStore.model.Comment;
import hkmu.comps380f.BookStore.view.DownloadingView;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/book")
public class BookController {

    public static class Form {
        private String name;
        private String author;
        private String description;
        private double price;
        private int number;
        private String commentText;
        private List<MultipartFile> attachments;
        // Getters and Setters of customerName, author, body, attachments

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getAuthor() {
            return author;
        }
        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getNumber() {
            return number;
        }
        public void setNumber(int number) {
            this.number = number;
        }
        public String getCommentText() {
            return commentText;
        }
        public void setCommentText(String commentText) {
            this.commentText = commentText;
        }
        public List<MultipartFile> getAttachments() {
            return attachments;
        }
        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    @Resource
    private BookService bService;


    @GetMapping(value = {"","/", "/list"}) //
    public String list(ModelMap model) {
        model.addAttribute("bookDatabase", bService.getBooks());
        model.addAttribute("attachmentDatabase", bService.getAttachments());
        return "list";
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("add", "bookForm", new Form());
    }

    @PostMapping("/create")
    public View create(Form form) throws IOException {
        long bookId = bService.createBook(form.getName(), form.getAuthor(), form.getNumber(), form.getAttachments());
        return new RedirectView("/book/view/" + bookId, true);
    }


    @GetMapping("/view/{bookId}")
    public String view(@PathVariable("bookId") long bookId, ModelMap model) throws BookNotFound {
        Book book = bService.getBook(bookId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("book", book);
        model.addAttribute("comments", book.getComments());
        return "view";
    }

    @PostMapping("/view/{bookId}/addComment")
    public RedirectView addComment(@PathVariable("bookId") long bookId, @RequestParam("text") String text)
            throws BookNotFound {
        Book book = bService.getBook(bookId);
        Comment newComment = new Comment();
        newComment.setText(text);
        newComment.setBook(book);
        book.getComments().add(newComment);
        bService.saveBook(book);

        return new RedirectView("/book/view/" + bookId, true);
    }

    @GetMapping("/{bookId}/attachment/{attachment:.+}")
    public View download(@PathVariable("bookId") long bookId, @PathVariable("attachment") UUID attachmentId) throws BookNotFound, AttachmentNotFound {
        Attachment attachment = bService.getAttachment(bookId, attachmentId);
        return new DownloadingView(attachment.getName(), attachment.getMimeContentType(), attachment.getContents());
    }

    @ExceptionHandler({BookNotFound.class, AttachmentNotFound.class})
    public ModelAndView error(Exception e) {
        return new ModelAndView("error", "message", e.getMessage());
    }


    // TASK 8
    @GetMapping("/edit/{bookId}")
    public ModelAndView bookEditForm(@PathVariable("bookId") long bookId, Principal principal, HttpServletRequest request) throws BookNotFound{
        Book book = bService.getBook(bookId);
        if (book == null || !request.isUserInRole("ROLE_ADMIN")  ) {
            return new ModelAndView(new RedirectView("/book/list", true));
        }
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("book",book);
        Form bookForm = new Form();
        bookForm.setName(book.getName());
        bookForm.setAuthor(book.getAuthor());
        bookForm.setNumber(book.getNumber());
        modelAndView.addObject("bookForm",bookForm);
        return modelAndView;
    }

    // TASK 8
    @PostMapping("/edit/{bookId}")
    public String updateBook(@PathVariable("bookId") long bookId, Form form , Principal principal ,HttpServletRequest request) throws BookNotFound, IOException {
        Book book = bService.getBook(bookId);
        if (book == null || !request.isUserInRole("ROLE_ADMIN") ) {
            return "redirect:/book/list";
        }
        bService.updateBook(bookId, form.getName(), form.getAuthor(), form.getNumber(), form.getAttachments());
        return "redirect:/book/view/" + bookId;

    }


}

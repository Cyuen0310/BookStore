package hkmu.comps380f.BookStore.dao;

import hkmu.comps380f.BookStore.model.Book;
import hkmu.comps380f.BookStore.exception.AttachmentNotFound;
import hkmu.comps380f.BookStore.exception.BookNotFound;
import hkmu.comps380f.BookStore.model.Attachment;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    @Resource
    private BookRepository tRepo;

    @Resource
    private AttachmentRepository aRepo;

    @Transactional
    public List<Book> getBooks() {
        return tRepo.findAll();
    }

    @Transactional
    public Book getBook(long id) throws BookNotFound {
        Book book = tRepo.findById(id).orElse(null);
        if (book == null) {
            throw new BookNotFound(id);
        }
        return book;
    }

    @Transactional
    public List<Attachment> getAttachments() {
        return aRepo.findAll();
    }
    @Transactional
    public Attachment getAttachment(long bookId, UUID attachmentId) throws BookNotFound, AttachmentNotFound {
        Book book = tRepo.findById(bookId).orElse(null);
        if (book == null) {
            throw new BookNotFound(bookId);
        }
        Attachment attachment = aRepo.findById(attachmentId).orElse(null);
        if (attachment == null) {
            throw new AttachmentNotFound(attachmentId);
        }
        return attachment;
    }

    @Transactional(rollbackFor = BookNotFound.class)
    public void delete(long id) throws BookNotFound {
        Book deletedBook = tRepo.findById(id).orElse(null);
        if (deletedBook == null) {
            throw new BookNotFound(id);
        }
        tRepo.delete(deletedBook);
    }

    @Transactional(rollbackFor = AttachmentNotFound.class)
    public void deleteAttachment(long bookId, UUID attachmentId) throws BookNotFound, AttachmentNotFound {
        Book book = tRepo.findById(bookId).orElse(null);
        if (book == null) {
            throw new BookNotFound(bookId);
        }
        for (Attachment attachment : book.getAttachments()) {
            if (attachment.getId().equals(attachmentId)) {
                book.deleteAttachment(attachment);
                tRepo.save(book);
                return;
            }
        }
        throw new AttachmentNotFound(attachmentId);
    }

    @Transactional
    public long createBook(String name, String author, int number, List<MultipartFile> attachments) throws IOException {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setNumber(number);
        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setBook(book);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                book.getAttachments().add(attachment);
            }
        }
        Book savedBook = tRepo.save(book);
        return savedBook.getId();
    }

    @Transactional
    public void updateBook(long id, String name, String author, int number, List<MultipartFile> attachments) throws BookNotFound, IOException {
        Book updatedBook = tRepo.findById(id).orElse(null);
        if (updatedBook == null){
            throw new BookNotFound(id);
        }
        updatedBook.setName(name);
        updatedBook.setAuthor(author);
        updatedBook.setNumber(number);
        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setBook(updatedBook);
            if (attachment.getName() != null && !attachment.getName().isEmpty()
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                updatedBook.getAttachments().add(attachment);
            }
        }
        tRepo.save(updatedBook);
    }

    public Optional<Attachment> getAttachmentsForBook(UUID attachmentId) {
        return aRepo.findById(attachmentId);
    }

    public Attachment getAttachment(UUID attachmentId) throws AttachmentNotFound {
        return aRepo.findById(attachmentId).orElseThrow(() -> new AttachmentNotFound(attachmentId));
    }

    @Transactional
    public Book saveBook(Book book) {
        return tRepo.save(book);
    }
}

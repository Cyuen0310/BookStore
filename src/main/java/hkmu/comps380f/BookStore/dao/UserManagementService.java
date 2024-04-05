package hkmu.comps380f.BookStore.dao;

import hkmu.comps380f.BookStore.model.BookUser;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserManagementService {
    @Resource
    private BookUserRepository uRepo;

    @Transactional
    public List<BookUser> getBookUsers() {
        return uRepo.findAll();
    }

    @Transactional
    public BookUser getBookUser(String name){
        BookUser user = uRepo.findById(name).orElse(null);
        if(user == null){
            throw new UsernameNotFoundException("User '" + name + "' not found.");
        }
        return user;
    }

    @Transactional
    public void delete(String username) {
        BookUser bookUser = uRepo.findById(username).orElse(null);
        if (bookUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        uRepo.delete(bookUser);
    }

    @Transactional
    public void createBookUser(String username, String password, String[] roles) {
        BookUser user = new BookUser(username, password, roles);
        uRepo.save(user);
    }

    @Transactional(rollbackFor = UsernameNotFoundException.class)
    public void updateBookUser(String username, String newName, String password) throws UsernameNotFoundException{
        BookUser user = uRepo.findById(username).orElse(null);
        if (user == null){
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        user.setUsername(newName);
        user.setPassword("{noop}" + password);
        uRepo.save(user);

    }
}

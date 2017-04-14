package service;

import entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ContactRepository;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Integer addContact(Contact newContact) {
        return contactRepository.save(newContact).getId();
    }

    public void deleteContact(Integer id) {
        contactRepository.delete(id);
    }

    public void updateContact(Contact contact) {
        contactRepository.update(contact.getName(), contact.getBirthday(), contact.getId());
    }
}

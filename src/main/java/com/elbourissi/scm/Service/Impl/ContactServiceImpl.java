package com.elbourissi.scm.Service.Impl;

import com.elbourissi.scm.Entity.Contact;
import com.elbourissi.scm.Entity.User;
import com.elbourissi.scm.Helper.ResourceNotFound;
import com.elbourissi.scm.Repository.ContactRepository;
import com.elbourissi.scm.Service.ContactService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * @param contact
     * @return
     */
    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepository.save(contact);
    }

    /**
     * @param contact
     * @return
     */
    @Override
    public Contact update(Contact contact) {
        var contactOld = contactRepository.findById(contact.getId())
                .orElseThrow(() -> new ResourceNotFound("Contact not found"));
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setDescription(contact.getDescription());
        contactOld.setPicture(contact.getPicture());
        contactOld.setFavorite(contact.getFavorite());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());

        return contactRepository.save(contactOld);
    }

    /**
     * @return
     */
    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Contact getById(String id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Contact not found"));
    }

    /**
     * @param id
     */
    @Override
    public void delete(String id) {
        Contact deletedId = getById(id);
        contactRepository.delete(deletedId);
    }

    /**
     * @param nameKeyword
     * @param size
     * @param page
     * @param sortBy
     * @param order
     * @param user
     * @return
     */
    @Override
    public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("asc") ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    /**
     * @param emailKeyword
     * @param size
     * @param page
     * @param sortBy
     * @param order
     * @param user
     * @return
     */
    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("asc") ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByUserAndEmailContaining(user, emailKeyword, pageable);
    }

    /**
     * @param phoneNumberKeyword
     * @param size
     * @param page
     * @param sortBy
     * @param order
     * @param user
     * @return
     */
    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("asc") ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByUserAndPhoneNumberContaining(user, phoneNumberKeyword, pageable);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepository.findByUserId(userId);
    }

    /**
     * @param user
     * @param page
     * @param size
     * @param sortField
     * @param sortDirection
     * @return
     */
    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortField, String sortDirection) {
       Sort sort = sortDirection.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortDirection).descending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByUser(user, pageable);
    }
}

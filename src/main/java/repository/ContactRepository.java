package repository;


import org.springframework.data.jpa.repository.JpaRepository;
import entity.Contact;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Modifying
    @Query("update Contact c set c.name = :name, c.birthday = :birthday where c.id = :id")
    void update(@Param("name") String name, @Param("birthday") String birthday, @Param("id") Integer id);

}

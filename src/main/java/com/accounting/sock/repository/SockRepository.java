package com.accounting.sock.repository;

import com.accounting.sock.entity.Sock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SockRepository extends CrudRepository<Sock, Long> {

    @Query("select s from Sock s where s.color = ?1 and s.cottonPart = ?2")
    Sock findByColorAndCottonPart(String color, Integer cottonPart);

    @Query("select coalesce(sum(s.quantity), 0) from Sock s where s.color = ?1 and s.cottonPart > ?2")
    Long getSockCountMoreThanValue(String color, Integer cottonPart);

    @Query("select coalesce(sum(s.quantity), 0) from Sock s where s.color = ?1 and s.cottonPart < ?2")
    Long getSockCountLessThanValue(String color, Integer cottonPart);

    @Query("select coalesce(sum(s.quantity), 0) from Sock s where s.color = ?1 and s.cottonPart = ?2")
    Long getSockCountEqualValue(String color, Integer cottonPart);
}

package com.example.cowinvaccineavailablityslotapplication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PincodeAndLinkDb extends JpaRepository<PincodeAndLink,String> {

    List<PincodeAndLink> findByLink(String link);
    List<PincodeAndLink> findByLinkNotNull();
    PincodeAndLink findByPincode(String pincode);
}

/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 07-May-21
 *   Time: 6:12 PM
 *   File: PinCodeAndLinkService.java
 */

package com.example.cowinvaccineavailablityslotapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PinCodeAndLinkService {  @Autowired
PincodeAndLinkDb pincodeAndLinkDb;

    public String findLink(String pincode) {
        PincodeAndLink newP = pincodeAndLinkDb.findByPincode(pincode);
        try {
            if (!newP.getLink().equalsIgnoreCase("null") || !(newP == null) ) {
                return newP.getLink();
            }
            return "Wait for a time , link not generated yet";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Wait for a time , link not generated yet";
    }

    public void save(String pincode) {
        Optional<PincodeAndLink> check = pincodeAndLinkDb.findById(pincode);
        PincodeAndLink pincodelink = new PincodeAndLink(pincode, null);
        if (check != null) {
            pincodeAndLinkDb.save(pincodelink);
        }
    }

    public List<PincodeAndLink> getAllPincode() {
        return pincodeAndLinkDb.findByLink(null);
    }

    public void saveDetail(PincodeAndLink pincodeAndLink) {
        pincodeAndLinkDb.save(pincodeAndLink);
    }

    public List<PincodeAndLink> getAll() {
        return pincodeAndLinkDb.findByLinkNotNull();
    }
}


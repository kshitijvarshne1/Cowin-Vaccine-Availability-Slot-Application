/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 07-May-21
 *   Time: 6:12 PM
 *   File: Controller.java
 */

package com.example.cowinvaccineavailablityslotapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller { @Autowired
PinCodeAndLinkService pinCodeAndLinkService;

    @GetMapping("/getAllPincodeHavingOnlyPincode")
    public List<PincodeAndLink> getAllPincodeHavingOnlyPincode() {
        return pinCodeAndLinkService.getAllPincode();
    }

    @PostMapping("/insertDetail")
    public void insertDetail(@RequestBody PincodeAndLink pincodeAndLink) {
        pinCodeAndLinkService.saveDetail(pincodeAndLink);
    }

    @GetMapping("/getAll")
    public List<PincodeAndLink> getAll() {
        return pinCodeAndLinkService.getAll();
    }
}


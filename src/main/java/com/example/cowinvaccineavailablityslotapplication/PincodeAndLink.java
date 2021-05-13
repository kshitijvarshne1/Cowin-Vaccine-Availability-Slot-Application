/*   Created by IntelliJ IDEA.
 *   Author: Kshitij Varshney (kshitijvarshne1)
 *   Date: 07-May-21
 *   Time: 6:14 PM
 *   File: PincodeAndLink.java
 */

package com.example.cowinvaccineavailablityslotapplication;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class PincodeAndLink {
    @Id
    String pincode;
    String link;

    public PincodeAndLink(String pincode, String link) {
        this.pincode = pincode;
        this.link = link;
    }
    public PincodeAndLink(){
    }

}


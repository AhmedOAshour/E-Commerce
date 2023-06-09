package com.vodafone.ecommerce.model;

import jakarta.persistence.*;
import lombok.Setter;


@Entity
@Setter
@DiscriminatorValue(value = "1")
public class Admin extends User{

}

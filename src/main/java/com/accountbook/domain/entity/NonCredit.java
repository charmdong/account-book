package com.accountbook.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("NON_CREDIT")
public class NonCredit extends Asset {

}

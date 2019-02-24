package com.ibexsys.toolkit.jpa.hibernate.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class FullTimeEmployee extends Employee {
	private static Logger logger = LoggerFactory.getLogger(FullTimeEmployee.class);

	private BigDecimal salary;

	protected FullTimeEmployee() {
	};

	public FullTimeEmployee(String name, BigDecimal salary) {
		super(name);
		this.salary = salary;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

}

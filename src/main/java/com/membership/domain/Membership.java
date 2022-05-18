package com.membership.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity 
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
public class Membership 
{
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	private LocalDate startDate;
	private LocalDate endDate;    

	@ManyToOne
	@JoinColumn(name = "plan_id")
	private Plan plan;
	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Transaction> transactions = new ArrayList<Transaction>();
	public Membership(LocalDate startDate, LocalDate endDate, Member member, Plan plan, Location location)
	{
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.plan = plan;
	}
	public Long getId() 
	{
		return id;
	}
	public LocalDate getStartDate() 
	{
		return startDate;
	}
	public LocalDate getEndDate() 
	{
		return endDate;
	}
	public Location getLocation() 
	{
		return location;
	}
	public void setLocation(Location location) 
	{
		this.location = location;
	}
	public Plan getPlan() 
	{
		return plan;
	}
	public void setPlan(Plan plan) 
	{
		this.plan = plan;
	}
	public List<Transaction> getTransactions() 
	{
		return transactions;
	}
	public void setTransations(List<Transaction> transactions) 
	{
		this.transactions = transactions;
	}
	public void addTransaction(Transaction transaction) 
	{
		this.transactions.add(transaction);
	}
	@Override
	public int hashCode() 
	{
		return Objects.hash(startDate, id, endDate);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Membership other = (Membership) obj;
		return Objects.equals(startDate, other.startDate) && Objects.equals(id, other.id) && Objects.equals(endDate, other.endDate);
	}	
}
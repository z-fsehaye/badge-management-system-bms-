package com.membership.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@NoArgsConstructor @Setter @Getter
@Table(name="badges")
public class Badge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "badge_id")
	private Long id;
	@Column(name="is_active",nullable = false, columnDefinition="boolean default false")
	private boolean isActive;
	@Column(name="issue_date")
	private LocalDate issueDate;
	@Column(name="expiration_date")
	private LocalDate expirationDate;
	@JsonIgnore
	@ManyToOne
	private Member member;
	
	public Badge(boolean isActive, LocalDate issueDate, LocalDate expirationDate) {
		super();
		this.isActive = isActive;
		this.issueDate = issueDate;
		this.expirationDate = expirationDate;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(expirationDate, id, issueDate);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Badge other = (Badge) obj;
		return Objects.equals(expirationDate, other.expirationDate) && Objects.equals(id, other.id)
				&& Objects.equals(issueDate, other.issueDate);
	}
	
	
	
}

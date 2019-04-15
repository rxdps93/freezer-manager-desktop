package com.dom.freeman.components.users;

import java.util.Comparator;

import com.dom.freeman.obj.SortMode;
import com.dom.freeman.obj.users.User;

public enum UserSortMode implements SortMode {

	// Sort by first name from A to Z
	FIRST_ASC(new Comparator<User>() {
		@Override
		public int compare(User o1, User o2) {
			return o1.getFirstName().compareTo(o2.getFirstName());
		}
	}),
	
	// Sort by first name from Z to A
	FIRST_DESC(new Comparator<User>() {
		@Override
		public int compare(User o1, User o2) {
			return -(o1.getFirstName().compareTo(o2.getFirstName()));
		}
	}),
	
	// Sort by last name from A to Z
	LAST_ASC(new Comparator<User>() {
		@Override
		public int compare(User o1, User o2) {
			return o1.getLastName().compareTo(o2.getLastName());
		}
	}),
	
	// Sort by last name from Z to A
	LAST_DESC(new Comparator<User>() {
		@Override
		public int compare(User o1, User o2) {
			return -(o1.getLastName().compareTo(o2.getLastName()));
		}
	}),
	
	// Sort by display name from A to Z
	DISP_ASC(new Comparator<User>() {
		@Override
		public int compare(User o1, User o2) {
			return o1.getDisplayName().compareTo(o2.getDisplayName());
		}
	}),
	
	// Sort by display name from Z to A
	DISP_DESC(new Comparator<User>() {
		@Override
		public int compare(User o1, User o2) {
			return -(o1.getDisplayName().compareTo(o2.getDisplayName()));
		}
	});
	
	private final Comparator<User> sortMethod;
	
	private UserSortMode(Comparator<User> sortMethod) {
		this.sortMethod = sortMethod;
	}
	
	@Override
	public Comparator<User> getSortMethod() {
		return this.sortMethod;
	}
}

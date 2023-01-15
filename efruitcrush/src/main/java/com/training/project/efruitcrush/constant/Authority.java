package com.training.project.efruitcrush.constant;

public class Authority {
	public static final String[] USER_AUTHORITIES = {
			"fruit:read, cart:read, cart:update, cart:write, order:read, order:write, order:delete" };
	public static final String[] ADMIN_AUTHORITIES = { "fruit:read, fruit:write, fruit:update, order:read" };
}

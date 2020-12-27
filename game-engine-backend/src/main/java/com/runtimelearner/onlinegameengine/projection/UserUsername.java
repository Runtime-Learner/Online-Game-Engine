package com.runtimelearner.onlinegameengine.projection;

/**
 * Projection used for searches. Should not load whole user onject when all we need is the username.
 * @author matt
 *
 */
public interface UserUsername {
	String getUsername();
}

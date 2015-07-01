package me.wingedmlgpro.swiftyguard.util;

import org.bukkit.util.*;

/**
 * © This Document and Code is STRICTLY copyrighted(©) to Ben.
 * © If anyone takes any part of this code and uses it for
 * © Something public, Share with someone, uses it for API's,
 * © implementing it to their code and taking the rights for
 * © it is NOT allowed unless you get permission from me!
 * © This project SwiftyGuard was created by 35047
 * © at 6/06/15 at 12:59 PM
 */
public class WandPoint {

	private Vector p1 = null, p2 = null;
	private boolean initialised = false;

	public WandPoint(double p1X, double p1Y, double p1Z, double p2X, double p2Y, double p2Z) {
		this.p1 = new Vector(p1X, p1Y, p1Z);
		this.p2 = new Vector(p2X, p2Y, p2Z);
	}

	public double getP1X() {
		return this.p1.getX();
	}

	public double getP1Y() {
		return this.p1.getY();
	}

	public double getP1Z() {
		return this.p1.getZ();
	}

	public double getP2X() {
		return this.p2.getX();
	}

	public double getP2Y() {
		return this.p2.getY();
	}

	public double getP2Z() {
		return this.p2.getZ();
	}

	public boolean isInitialised() {
		return this.initialised;
	}

	public void setP1X(double p1X) {
		this.p1.setX(p1X);
		this.initialised = true;
	}

	public void setP1Y(double p1Y) {
		this.p1.setY(p1Y);
		this.initialised = true;
	}

	public void setP1Z(double p1Z) {
		this.p1.setZ(p1Z);
		this.initialised = true;
	}

	public void setP2X(double p2X) {
		this.p2.setX(p2X);
		this.initialised = true;
	}

	public void setP2Y(double p2Y) {
		this.p2.setY(p2Y);
		this.initialised = true;
	}

	public void setP2Z(double p2Z) {
		this.p2.setZ(p2Z);
		this.initialised = true;
	}

}

package relampagorojo93.MMOHorses.Enums;

import relampagorojo93.MMOHorses.Modules.FilePckg.Settings.SettingWalkmode;

public enum WalkMode {
	HALT, WALK, TROT, CANTER, GALLOP;
	
	public double getSpeedMultiplier() {
		return (double) this.ordinal()/((double) WalkMode.values().length - 1D);
	}
	
	public double getJumpMultiplier() {
		return this.ordinal() >= SettingWalkmode.WALKMODEFORMAXJUMP.toWalkMode().ordinal() ? 1D : (double) this.ordinal()/(double) SettingWalkmode.WALKMODEFORMAXJUMP.toWalkMode().ordinal();
	}
	
	@Override
	public String toString() {
		return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
	}
}

package net.juniper.jmp.wtf.cache;

public interface ICache {
	public void addCache(String key, Object value);
	public Object getCache(String key);
	public void clearCache();
}

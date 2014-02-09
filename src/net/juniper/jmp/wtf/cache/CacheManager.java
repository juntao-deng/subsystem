package net.juniper.jmp.wtf.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CacheManager {
	private Map<String, ICache> cacheMap = new ConcurrentHashMap<String, ICache>();
	private static CacheManager instance = new CacheManager();
	private CacheManager() {
		
	}
	
	public static CacheManager getInstance() {
		return instance;
	}
	/**
	 * return a strong cache
	 * @param cacheId
	 * @return
	 */
	public ICache getStrongCache(String cacheId){
		ICache cache = cacheMap.get(cacheId);
		if(cache == null){
			synchronized(cacheMap){
				cache = cacheMap.get(cacheId);
				if(cache == null){
					cache = new SimpleCacheImpl();
					cacheMap.put(cacheId, cache);
				}
			}
		}
		return cache;
	}
	
	/**
	 * TODO
	 * return a soft cache
	 * @param cacheId
	 * @return
	 */
	public ICache getSoftCache(String cacheId){
		ICache cache = cacheMap.get(cacheId);
		if(cache == null){
			synchronized(cacheMap){
				cache = cacheMap.get(cacheId);
				if(cache == null){
					cache = new SimpleCacheImpl();
					cacheMap.put(cacheId, cache);
				}
			}
		}
		return cache;
	}
	
	/**
	 * return a cross node cache
	 * @param cacheId
	 * @return
	 */
	public ICache getCrossNodeCache(String cacheId){
		return null;
	}
}

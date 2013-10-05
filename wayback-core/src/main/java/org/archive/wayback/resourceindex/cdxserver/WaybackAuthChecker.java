package org.archive.wayback.resourceindex.cdxserver;

import org.archive.cdxserver.auth.AuthToken;
import org.archive.cdxserver.auth.PrivTokenAuthChecker;
import org.archive.cdxserver.filter.CDXAccessFilter;
import org.archive.cdxserver.filter.FilenamePrefixFilter;
import org.archive.wayback.accesscontrol.robotstxt.redis.RedisRobotExclusionFilterFactory;
import org.archive.wayback.accesscontrol.staticmap.StaticMapExclusionFilterFactory;
import org.archive.wayback.resourceindex.filters.ExclusionFilter;

public class WaybackAuthChecker extends PrivTokenAuthChecker {
	
	protected StaticMapExclusionFilterFactory adminExclusions;
	protected RedisRobotExclusionFilterFactory robotsExclusions;
	
	protected FilenamePrefixFilter prefixFilter = null;
	
	public CDXAccessFilter createAccessFilter(AuthToken token)
	{
		ExclusionFilter adminFilter = null;
		if (adminExclusions != null) {
			adminFilter = adminExclusions.get();
		}
		
		ExclusionFilter robotsFilter = null;
		if (robotsExclusions != null) {
			robotsFilter = robotsExclusions.get();
		}
		
		return new AccessCheckFilter(token, adminFilter, robotsFilter, prefixFilter);
	}	
	
	public StaticMapExclusionFilterFactory getAdminExclusions() {
		return adminExclusions;
	}

	public void setAdminExclusions(StaticMapExclusionFilterFactory adminExclusions) {
		this.adminExclusions = adminExclusions;
	}

	public RedisRobotExclusionFilterFactory getRobotsExclusions() {
		return robotsExclusions;
	}

	public void setRobotsExclusions(
	        RedisRobotExclusionFilterFactory robotsExclusions) {
		this.robotsExclusions = robotsExclusions;
	}

	public FilenamePrefixFilter getPrefixFilter() {
		return prefixFilter;
	}

	public void setPrefixFilter(FilenamePrefixFilter prefixFilter) {
		this.prefixFilter = prefixFilter;
	}

//	@Override
//    public boolean isCaptureAllowed(CDXLine line, AuthToken auth) {		
//	    if (prefixFilter == null) {
//	    	return true;
//	    }
//	    
//		if (this.isAllUrlAccessAllowed(auth)) {
//			return true;
//		}
//	    
//	    return prefixFilter.include(line);
//    }
}
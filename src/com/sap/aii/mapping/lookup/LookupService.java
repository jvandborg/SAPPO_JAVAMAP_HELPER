/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.sap.aii.mapping.lookup;

import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;



public final class LookupService {
	static final String MAPPING_SERVICE_NAME = "Mapping";
	private static final Map serviceMap = new TreeMap();
	private static LookupService.Internal defaultService = new LookupServiceInternalImpl();

	public static SystemAccessor getSystemAccessor(Channel channel)
			throws LookupException {
		if (channel == null) {
			throw new NullPointerException("channel is null");
		}
		
		return new SystemAccessor(getService().getSystemAccessor(channel));
	}

	public static DataBaseAccessor getDataBaseAccessor(Channel channel)
			throws LookupException {
		if (channel == null) {
			throw new NullPointerException("channel is null");
		} else {
			return new DataBaseAccessor(getService().getSystemAccessor(channel));
		}
	}

	public static RfcAccessor getRfcAccessor(Channel channel)
			throws LookupException {
		if (channel == null) {
			throw new NullPointerException("channel is null");
		} else {
			return new RfcAccessor(getService().getSystemAccessor(channel));
		}
	}

	public static Channel getChannel(String party, String service,
			String channelName) throws LookupException {
		if (party == null) {
			throw new NullPointerException("Parameter party is null.");
		} else if (service == null) {
			throw new NullPointerException("Parameter service is null.");
		} else if (channelName == null) {
			throw new NullPointerException("Parameter channelName is null.");
		} else {
			byte[] bytes = getService().getChannelId(party, service,
					channelName);
			return ChannelHelper.createChannel(bytes, party, service,
					channelName);
		}
	}

	public static Channel getChannel(String service, String channelName)
			throws LookupException {
		if (service == null) {
			throw new NullPointerException("Parameter service is null.");
		} else if (channelName == null) {
			throw new NullPointerException("Parameter channelName is null.");
		} else {
			return getChannel("", service, channelName);
		}
	}

	public static XmlPayload getXmlPayload(InputStream stream) {
		if (stream == null) {
			throw new NullPointerException("stream is null");
		} else {
			return new XmlPayload(stream);
		}
	}

	public static TextPayload getTextPayload(InputStream stream, String encoding) {
		if (stream == null) {
			throw new NullPointerException("stream is null");
		} else {
			return new TextPayload(stream, encoding);
		}
	}

	public static BinaryPayload getBinaryPayload(InputStream stream) {
		if (stream == null) {
			throw new NullPointerException("stream is null");
		} else {
			return new BinaryPayload(stream);
		}
	}

	private static LookupService.Internal getService() throws LookupException {
		LookupService.Internal result = null;
		System.out.println("Number of registered services: "
				+ serviceMap.size());
		Object ob = LookupService.Internal.applicationId.get();
		if (ob != null && ob instanceof String) {
			System.out.println("Thread local application: " + ob);
			result = (LookupService.Internal) serviceMap.get(ob);
			if (result == null) {
				System.out
						.println("Service map has not a service for the application.");
				result = defaultService;
			} else {
				System.out.println("Service found for application.");
			}
		} else {
			System.out
					.println("Thread local application is null or not a string: "
							+ ob + ". The default service is returned.");
			result = defaultService;
		}

		if (result == null) {
			throw new LookupException(
					"Internal lookup service is not registered. Invoking the lookup service is only supported in the XI Enterprise Services Builder test environment or in the Integration Server runtime environment. In the standalone Enterprise Services Builder environment the calling of the service is not supported.",
					false);
		} else {
			return result;
		}
	}

	public abstract static class Internal {
		public static final ThreadLocal applicationId = new InheritableThreadLocal();

		public abstract com.sap.aii.mapping.lookup.SystemAccessor.Internal getSystemAccessor(
				Channel channel) throws LookupException;

		public abstract byte[] getChannelId(String party, String service,
				String channelName);

		public static synchronized void register(LookupService.Internal aService) {
			// if (aService == null) {
			// throw new NullPointerException("service is null");
			// } else {
			// String className = aService.getClass().getName();
			// if (!className
			// .equals("com.sap.aii.ibrun.server.lookup.LookupRegistration$LocalClient")
			// && !className
			// .equals("com.sap.aii.ibrep.server.lookup.LookupServiceProvider$RemoteClient"))
			// {
			// throw new IllegalArgumentException("The class " + className
			// + " is not a valid service.");
			// } else {
			// Object appl = applicationId.get();
			// if (!"REPOSITORY".equals(appl) && !"RUNTIME".equals(appl)) {
			// throw new IllegalArgumentException(
			// "The thread local application Id "
			// + appl
			// +
			// " is not valid. It is neither the repostiory nor the runtime ID.");
			// } else {
			// if ("RUNTIME".equals(appl)) {
			// LookupService.defaultService = aService;
			// } else if (LookupService.defaultService == null) {
			// LookupService.defaultService = aService;
			// } else if
			// ("com.sap.aii.ibrep.server.lookup.LookupServiceProvider$RemoteClient"
			// .equals(LookupService.defaultService.getClass()
			// .getName())) {
			// LookupService.defaultService = aService;
			// }
			//
			// LookupService.serviceMap.put(appl, aService);
			// }
			// }
			// }
		}
	}
}
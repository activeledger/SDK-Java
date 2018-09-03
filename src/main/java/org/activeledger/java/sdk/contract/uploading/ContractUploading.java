package org.activeledger.java.sdk.contract.uploading;

import org.activeledger.java.sdk.nhpk.NHPK;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("ContractUploading")
public class ContractUploading {

	private static final Logger logger = Logger.getLogger(ContractUploading.class);

	@Autowired
	ContractUploadingReq contractUploadingReq;
	@Autowired
	NHPK nhpk1;
	ObjectMapper mapper;

	public ContractUploading() {
		mapper = new ObjectMapper();
	}

	public JSONObject uploadContract(ContractUploadingTransaction contractUploadingTransaction) throws Exception {

		logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(contractUploadingTransaction));
		JSONObject jsonObj = new JSONObject(contractUploadingReq.uploadContract(contractUploadingTransaction));
		logger.debug(jsonObj);
		return jsonObj;

	}

}

package cn.gotoil.znl.common.tools;



import cn.gotoil.znl.web.message.response.CommonResponse;

import java.util.HashMap;
import java.util.Map;



public class RetUtil {





	public static CommonResponse getRetValue(boolean b, Object datas, String msg, int code){

		CommonResponse response = new CommonResponse();

		response.setSuccess(b);
		response.setCode(code);
		response.setDatas(datas);
		response.setMsg(msg);

		return response;
	}

	public static CommonResponse getRetValue(boolean b, Object datas, String msg){

		CommonResponse response = new CommonResponse();

		response.setSuccess(b);
		response.setCode(response.NormalStatusCode);
		response.setDatas(datas);
		response.setMsg(msg);

		return response;
	}

	public static CommonResponse getRetValue(boolean b , String msg, Object datas){

		CommonResponse response = new CommonResponse();

		response.setSuccess(b);
		response.setCode(response.NormalStatusCode);
		response.setDatas(datas);
		response.setMsg(msg);

		return response;
	}

	public static CommonResponse getRetValue(boolean b , String msg){

		CommonResponse response = new CommonResponse();

		response.setSuccess(b);
		response.setCode(response.NormalStatusCode);
		response.setDatas(new HashMap<String,Object>());
		response.setMsg(msg);


		return response;
	}
	public static CommonResponse getRetValue(boolean b , Object datas){

		CommonResponse response = new CommonResponse();

		response.setSuccess(b);
		response.setCode(response.NormalStatusCode);
		response.setDatas(new HashMap<String,Object>());
		response.setMsg("");



		return response;
	}


	public static CommonResponse getRetValue(boolean b  ){

		CommonResponse response = new CommonResponse();

		response.setSuccess(b);
		response.setCode(response.NormalStatusCode);
		response.setDatas(new HashMap<String,Object>());
		response.setMsg("");


		return response;
	}

	

}

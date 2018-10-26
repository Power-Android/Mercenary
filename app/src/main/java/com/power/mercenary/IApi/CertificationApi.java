package com.power.mercenary.IApi;

import com.power.mercenary.bean.BankNameBean;
import com.power.mercenary.bean.SuccessBean;
import com.power.mercenary.constantapi.ConstantApi;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface CertificationApi {

    @Multipart
    @POST(ConstantApi.CERTIFICATION_URL)
    Observable<SuccessBean> SuccessBean(@FieldMap Map<String,String> map,@Part MultipartBody.Part part);

    @FormUrlEncoded
    @POST(ConstantApi.GETBANKNAME_URL)
    Observable<BankNameBean> bankNameBean(@Query("cardNo") String cardNo);

}

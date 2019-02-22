package tv.cloudwalker.cwlibrary;

import android.os.RemoteException;

import com.cvte.tv.api.TvApiApplication;
import com.cvte.tv.api.TvServiceConnectListener;
import com.cvte.tv.api.aidl.EntityInputSource;
import com.cvte.tv.api.aidl.ITVApiScreenWindowAidl;
import com.cvte.tv.api.aidl.ITVApiSystemInputSourceAidl;
import com.cvte.tv.api.aidl.ITvApiManager;

import java.util.List;

public class CloudwalkerTvApi
{
    private ITVApiScreenWindowAidl tvScreenApi = null;
    private ITVApiSystemInputSourceAidl sourceApi = null;


    private void initCloudwalkerTvApi()
    {
        if(tvScreenApi == null || sourceApi == null)
        {
            TvApiApplication.getTvApi(new TvServiceConnectListener() {
                @Override
                public void OnConnected(ITvApiManager iTvApiManager) {
                    try {
                        tvScreenApi = iTvApiManager.getTVApiScreenWindow();
                        sourceApi = iTvApiManager.getTVApiSystemInputSource();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public ITVApiScreenWindowAidl getTvScreenApi()
    {
        if(tvScreenApi == null) {
            initCloudwalkerTvApi();
        }
        return  tvScreenApi;
    }

    public ITVApiSystemInputSourceAidl getTvSourceApi()
    {
        if(sourceApi == null) {
            initCloudwalkerTvApi();
        }
        return sourceApi;
    }

    public List<EntityInputSource> getTvApiSourceList()
    {
        if(sourceApi == null)
        {
            initCloudwalkerTvApi();
        }
        try {
            return sourceApi.eventSystemInputSourceGetList();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean scaleTvScreenWindow(int left , int top , int width, int height)
    {
        if(tvScreenApi != null)
        {
            try {
                return  tvScreenApi.eventScreenWindowSetPipValue(left, top, width, height);
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }else {
            return  false;
        }
    }



}

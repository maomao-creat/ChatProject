package com.zykj.samplechat.rongc.provider;

import com.zykj.samplechat.ui.widget.App;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imlib.model.Conversation;

/**
 * Created by csh
 * Created date 2016/12/7.
 * Description 自定义Plugins
 */

public class SampleExtensionModule extends DefaultExtensionModule {

    private String targetId;

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        //super.getPluginModules(conversationType);
        List<IPluginModule> pluginModuleList = new ArrayList<>();
       //pluginModuleList.add(new LocationPlugin());//地图
        //pluginModuleList.add(new VideoIPluginModule());小视频

        if(conversationType.equals(Conversation.ConversationType.GROUP)) {
            if (App.targetId.equals("571")) {
                pluginModuleList.add(new FastIPluginModule(0));
                pluginModuleList.add(new FastIPluginModule(1));
                pluginModuleList.add(new ImagePlugin());
                pluginModuleList.add(new SendMoneyProvider());
                //pluginModuleList.add(new FastIPluginModule(2));
            }else{
                pluginModuleList.add(new FastIPluginModule(0));
                pluginModuleList.add(new FastIPluginModule(1));
                pluginModuleList.add(new ImagePlugin());
                pluginModuleList.add(new SendMoneyProvider());
                //  pluginModuleList.add(new FastIPluginModule(2));
            }
//            pluginModuleList.add(new FastIPluginModule(0));
//            pluginModuleList.add(new SendMoneyProvider());
//            pluginModuleList.add(new FastIPluginModule(1));
//            pluginModuleList.add(new FastIPluginModule(2));
        }else{
            pluginModuleList.add(new ImagePlugin());
            pluginModuleList.add(new FastIPluginModule(2));
//            pluginModuleList.add(new SendMoneyProvider());
        }
        return pluginModuleList;
    }
}
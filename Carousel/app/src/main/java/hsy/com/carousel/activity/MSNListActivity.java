package hsy.com.carousel.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import hsy.com.carousel.R;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.CSCustomServiceInfo;
import io.rong.imlib.model.Conversation;

public class MSNListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msnlist);
    }

    public void MSNOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(MSNListActivity.this, "xxf", "title");//15388881111
                break;
            case R.id.btn2:
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startConversationList(this);
                break;
            case R.id.btn3:
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startSubConversationList(MSNListActivity.this, Conversation.ConversationType.GROUP);
                break;
            case R.id.btn4:
                //首先需要构造使用客服者的用户信息
                CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
                CSCustomServiceInfo csInfo = csBuilder.nickName("融云").build();
                /**
                 * 启动客户服聊天界面。
                 *
                 * @param context           应用上下文。
                 * @param customerServiceId 要与之聊天的客服 Id。
                 * @param title             聊天的标题，如果传入空值，则默认显示与之聊天的客服名称。
                 * @param customServiceInfo 当前使用客服者的用户信息。{@link io.rong.imlib.model.CSCustomServiceInfo}
                 */
                RongIM.getInstance().startCustomerServiceChat(MSNListActivity.this, "KEFU147142560835276", "在线客服", csInfo);

                break;
            case R.id.btn5:
                finish();
                break;
        }
    }
}

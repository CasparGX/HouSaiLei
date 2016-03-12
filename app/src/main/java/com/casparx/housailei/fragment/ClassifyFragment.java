package com.casparx.housailei.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.casparx.housailei.DemoAdapter;
import com.casparx.housailei.R;
import com.casparx.housailei.model.DemoModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClassifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassifyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.classify_gridview)
    GridView classifyGridview;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<DemoModel> demoModelList;

    private Resources resources;

    private OnFragmentInteractionListener mListener;

    public ClassifyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassifyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassifyFragment newInstance(String param1, String param2) {
        ClassifyFragment fragment = new ClassifyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_classify, container, false);
        ButterKnife.bind(this, view);
        init(inflater);
        return view;
    }

    private void init(LayoutInflater inflater) {
        resources = getResources();
        initDemoData();
        DemoAdapter demoAdapter = new DemoAdapter(inflater, demoModelList);
        classifyGridview.setAdapter(demoAdapter);
        classifyGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showPopupWindowDemo(view, demoModelList.get(i));
            }
        });
    }

    private void initDemoData() {
        demoModelList = new ArrayList<DemoModel>();
        DemoModel demoModel1 = new DemoModel();
        demoModel1.setTitle("天空");
        demoModel1.setDec("要点：晴天的时候，将手机放在地上仰拍，设置定时，两人牵手摆出爱心的形状。");
        demoModel1.setResId(R.drawable.demo_img1);
        DemoModel demoModel2 = new DemoModel();
        demoModel2.setTitle("前方探路");
        demoModel2.setDec("要点：女生在前，男生在后，两人靠近，左手放在额头处做出远望的动作，配合上夸张的表情。");
        demoModel2.setResId(R.drawable.demo_img2);
        DemoModel demoModel3 = new DemoModel();
        demoModel3.setTitle("忘情舞蹈");
        demoModel3.setDec("要点：男生女生做出舞蹈的动作，眼神交流。");
        demoModel3.setResId(R.drawable.demo_img3);
        DemoModel demoModel4 = new DemoModel();
        demoModel4.setTitle("为您服务");
        demoModel4.setDec("要点：女生在前，男生在后，两人靠近，稍微鞠躬，把手合并放在膝盖上面，眼神往后。");
        demoModel4.setResId(R.drawable.demo_img4);
        DemoModel demoModel5 = new DemoModel();
        demoModel5.setTitle("放大镜");
        demoModel5.setDec("要点：需要道具（放大镜），女士拿着放大镜“观察”镜头，露出笑容，男生在一旁做出夸张的动作。");
        demoModel5.setResId(R.drawable.demo_img5);
        DemoModel demoModel6 = new DemoModel();
        demoModel6.setTitle("夫唱妇随");
        demoModel6.setDec("要点：需要道具（任何可以咬着的东西）男生抱着女生，男生下巴顶着女生的头看远处，女生低头看镜头。由第三人帮忙拍摄。");
        demoModel6.setResId(R.drawable.demo_img6);
        DemoModel demoModel7 = new DemoModel();
        demoModel7.setTitle("吃惊？吃面！");
        demoModel7.setDec("要点：需要道具（任何可以咬着的东西）。男女直起腰看向镜头，作出诧异的表情。");
        demoModel7.setResId(R.drawable.demo_img7);
        DemoModel demoModel8 = new DemoModel();
        demoModel8.setTitle("假装无脸");
        demoModel8.setDec("要点：把衣服反着穿（后面穿到前面），然后以背对着镜头，双手放在背后。");
        demoModel8.setResId(R.drawable.demo_img8);
        DemoModel demoModel9 = new DemoModel();
        demoModel9.setTitle("纸巾下巴");
        demoModel9.setDec("要点：需要道具（纸巾或者其他连在一起的东西），男生稍微抬头，望向天空，女生低头看向镜头。");
        demoModel9.setResId(R.drawable.demo_img9);

        /*demoModel1.setResId(R.drawable.me_touxiang));
        demoModel2.setResId(R.drawable.me_touxiang));
        demoModel3.setResId(R.drawable.me_touxiang));
        demoModel4.setResId(R.drawable.me_touxiang));
        demoModel5.setResId(R.drawable.me_touxiang));
        demoModel6.setResId(R.drawable.me_touxiang));
        demoModel7.setResId(R.drawable.me_touxiang));
        demoModel8.setResId(R.drawable.me_touxiang));
        demoModel9.setResId(R.drawable.me_touxiang));*/

        demoModelList.add(demoModel1);
        demoModelList.add(demoModel2);
        demoModelList.add(demoModel3);
        demoModelList.add(demoModel4);
        demoModelList.add(demoModel5);
        demoModelList.add(demoModel6);
        demoModelList.add(demoModel7);
        demoModelList.add(demoModel8);
        demoModelList.add(demoModel9);
    }


    private void showPopupWindowDemo(View view, DemoModel demoModel) {
        //自定义布局
        View contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.popupwindow_demo, null);
        TextView demoTitle = (TextView) contentView.findViewById(R.id.demo_title);
        TextView demoContent = (TextView) contentView.findViewById(R.id.demo_content);
        TextView demoTakePhoto = (TextView) contentView.findViewById(R.id.take_photo);
        ImageView demoPic = (ImageView) contentView.findViewById(R.id.demo_pic);
        demoContent.setText(demoModel.getDec());
        demoTitle.setText(demoModel.getTitle());
        //demoPic.setImageResource(demoModel.getResId());
        demoPic.setImageBitmap(DemoAdapter.readBitMap(getActivity(),demoModel.getResId()));

        ViewGroup.LayoutParams param = demoPic.getLayoutParams();
        param.width = (int) (view.getWidth()*2.5);
        param.height = param.width;
        demoPic.setLayoutParams(param);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        backgroundAlpha(0.5f, this.getActivity());
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f, getActivity());
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(resources.getDrawable(R.drawable.tablerow_default_bg));
        //popupWindow.setHeight((int) (view.getWidth()*3.5));
        popupWindow.setWidth((int) (view.getWidth()*2.5));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public static void backgroundAlpha(float bgAlpha, Activity activity)
    {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

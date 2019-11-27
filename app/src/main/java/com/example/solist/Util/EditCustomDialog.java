package com.example.solist.Util;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.solist.Database.ListVO;
import com.example.solist.R;
import com.example.solist.ViewModel.ListViewModel;

public class EditCustomDialog {

    private Context context;

    private EditText editContents;
    private Button changeButton;
    private Button cancelButton;

    private ListViewModel mViewModel;
    private ListVO listVO;

    public EditCustomDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(ListVO list, ListViewModel viewModel) {
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        Dialog dialog = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dialog.setContentView(R.layout.edit_dialog);
        // dialog 크기 설정 (.xml 파일에서 크기 조정이 되지 않아서 설정)
        WindowManager.LayoutParams wm = dialog.getWindow().getAttributes();
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width = WindowManager.LayoutParams.MATCH_PARENT;
        wm.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // 커스텀 다이얼로그를 노출한다.
        dialog.show();

        listVO = list;
        mViewModel = viewModel;

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        editContents = (EditText) dialog.findViewById(R.id.edit_contents);
        changeButton = (Button) dialog.findViewById(R.id.edit_change_button);
        cancelButton = (Button) dialog.findViewById(R.id.edit_cancel_button);

        editContents.setText(listVO.getContents());

        changeButton.setOnClickListener(view -> {
            listVO.setContents(editContents.getText().toString());
            mViewModel.update(listVO);
            // 커스텀 다이얼로그를 종료한다.
            dialog.dismiss();
        });
        cancelButton.setOnClickListener(view -> {
            // 커스텀 다이얼로그를 종료한다.
            dialog.dismiss();
        });
    }
}

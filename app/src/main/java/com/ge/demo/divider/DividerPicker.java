package com.ge.demo.divider;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import io.example.gjh.divider.R;

/**
 * Dialog for switching dividers.
 *
 * @author gejiaheng
 * @since 07-19-2016
 */
public class DividerPicker extends DialogFragment {

    public static final String EXTRA_SELECTED_DIVIDER = "extra_selected_divider";

    private int mSelectedDivider;

    public static DividerPicker newInstance(@MainActivity.DividerType int selectedDivider) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_SELECTED_DIVIDER, selectedDivider);
        DividerPicker picker = new DividerPicker();
        picker.setArguments(args);
        return picker;
    }

    private OnDividerSelectedListener mListener;

    public interface OnDividerSelectedListener {
        void onDividerSelected(DialogInterface dialogInterface, int i);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mSelectedDivider = args.getInt(EXTRA_SELECTED_DIVIDER, MainActivity.DIVIDER_UNDERNEATH);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (OnDividerSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.getClass().getName() + " must implement OnDividerSelectedListener.");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.divider_picker_title)
                .setSingleChoiceItems(R.array.dividers, mSelectedDivider, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mListener != null) {
                            mListener.onDividerSelected(dialogInterface, i);
                        }
                    }
                });
        return builder.create();
    }
}

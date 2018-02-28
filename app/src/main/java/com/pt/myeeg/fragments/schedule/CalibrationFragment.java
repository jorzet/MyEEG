package com.pt.myeeg.fragments.schedule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pt.myeeg.R;
import com.pt.myeeg.adapters.CalibrationCanvas;
import com.pt.myeeg.models.Palabras;
import com.pt.myeeg.fragments.content.BaseFragment;
import com.pt.myeeg.fragments.recording.RecordingFragment;
import com.pt.myeeg.services.database.InfoHandler;

/**
 * Created by Jorge Zepeda Tinoco on 26/07/17.
 * jorzet.94@gmail.com
 * */

public class CalibrationFragment extends BaseFragment {


    private Button mStartRecording;
    private TextView mSuggestion;
    private CalibrationCanvas mCalibrationCanvas;
    private TextView mModuleType;
    private TextView mElectrodeType;
    private TextView mCalibration;
    private ImageView mBatteryIcon;
    private TextView mBatteryText;

    private int[] mBateryPercentage = {
            R.drawable.ic_battery_20,
            R.drawable.ic_battery_30,
            R.drawable.ic_battery_50,
            R.drawable.ic_battery_60,
            R.drawable.ic_battery_80,
            R.drawable.ic_battery_90,
            R.drawable.ic_battery_100
    };

    private int[] mBateryPer = {90, 80, 80,
                                90, 100, 20, 90, 100,
                                100, 100, 60, 60, 20, 80, 90,
                                60, 60, 30, 90, 90,
                                100, 100};

    // FP1 G FP2 F7 F3 FZ F4 F8 A1 T3 C3 CZ C4 T4 A2 T5 P3 PZ P4 T6 O1 O2
    public int[] electrodesBatery = {5, 4, 4,
                                     5, 6, 0, 5, 6,
                                     6, 6, 3, 3, 0, 4, 5,
                                     3, 3, 1, 5, 5,
                                     6, 6};
    // FP1 G FP2 F7 F3 FZ F4 F8 A1 T3 C3 CZ C4 T4 A2 T5 P3 PZ P4 T6 O1 O2
    public int[] electrodesModule = {1, 1, 1,
                                     2, 1, 2, 5, 3,
                                     3, 3, 4, 4, 4, 6, 5,
                                     6, 6, 7, 7, 5,
                                     7, 5};

    private String[] chanels = {"FP1", "G", "FP2",
            "F7", "F3", "FZ", "F4", "F8",
            "A1", "T3", "C3", "CZ", "C4", "T4", "A2",
            "T5", "P3", "PZ", "P4", "T6",
            "O1", "O2"};

    private String[] electrodeColocation = {"No habilitado", "Mal Colocado", "Mal Colocado",
            "Correctamente Colocado", "Correctamente Colocado", "Bateria baja", "Correctamente Colocado", "Mal Colocado",
            "Correctamente Colocado", "Correctamente Colocado", "Correctamente Colocado", "Correctamente Colocado", "Bateria baja", "Correctamente Colocado", "Correctamente Colocado",
            "Correctamente Colocado", "Correctamente Colocado", "Mal Colocado", "Correctamente Colocado", "Correctamente Colocado",
            "Correctamente Colocado", "Correctamente Colocado"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(container == null)
            return null;

        View rootView = inflater.inflate(R.layout.calibration_fragment, container, false);

        mStartRecording = (Button) rootView.findViewById(R.id.start_recording);
        mSuggestion = (TextView) rootView.findViewById(R.id.spetialist_suggestion);
        mCalibrationCanvas = (CalibrationCanvas) rootView.findViewById(R.id.image_10_20_system);
        mModuleType = (TextView) rootView.findViewById(R.id.module_type);
        mElectrodeType = (TextView) rootView.findViewById(R.id.electrode_type);
        mCalibration = (TextView) rootView.findViewById(R.id.calibration);
        mBatteryIcon = (ImageView) rootView.findViewById(R.id.batery_icon);
        mBatteryText = (TextView) rootView.findViewById(R.id.batery_percentage);

        mCalibrationCanvas.setOnTouchListener(mCalibrationListener);
        mStartRecording.setOnClickListener(mStartRecordingListener);
        showSpetialistSuggestion();

        return rootView;
    }

    private Button.OnClickListener mStartRecordingListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            doRecording();
        }
    };

    private View.OnTouchListener mCalibrationListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.i("MyTAG: ","id: "+v.getId());
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                for(int i=0;i<mCalibrationCanvas.electrodes.length;i++) {
                    if (event.getX() > (float) (mCalibrationCanvas.percentageElectrode[i][0] * mCalibrationCanvas.width) &&
                            event.getX() < (float) (mCalibrationCanvas.percentageElectrode[i][0] * mCalibrationCanvas.width + 100) &&
                            event.getY() > (float) (mCalibrationCanvas.percentageElectrode[i][1] * mCalibrationCanvas.width) &&
                            event.getY() < (float) (mCalibrationCanvas.percentageElectrode[i][1] * mCalibrationCanvas.width + 100)){

                        mModuleType.setText("Módulo "+electrodesModule[i]);
                        mElectrodeType.setText(chanels[i]);
                        mCalibration.setText(electrodeColocation[i]);
                        mBatteryIcon.setImageResource(mBateryPercentage[electrodesBatery[i]]);
                        mBatteryText.setText(" "+ mBateryPer[i]+" %");
                    }
                }
                return true;
            }
            return false;
        }
    };

    private void doRecording(){
        getFragmentManager().beginTransaction().replace(R.id.fragment_container_schedule, new RecordingFragment()).commit();
    }

    private void showSpetialistSuggestion(){
        InfoHandler myHandler = new InfoHandler(getContext());
        String suggestions = myHandler.getExtraStored(Palabras.SPETIALIST_SUGGESTIONS);
        mSuggestion.setText(suggestions);
    }
}

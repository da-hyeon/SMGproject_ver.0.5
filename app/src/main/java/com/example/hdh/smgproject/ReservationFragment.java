package com.example.hdh.smgproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReservationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public String year;
    public String Month;
    public String Day;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ReservationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservationFragment newInstance(String param1, String param2) {
        ReservationFragment fragment = new ReservationFragment();
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

    private ArrayAdapter yearApapter;
    private Spinner yearSpinner;

    private ArrayAdapter monthApapter;
    private Spinner monthSpinner;

    private ArrayAdapter dayApapter;
    private Spinner daySpinner;

    private ArrayAdapter trainerApapter;
    private Spinner trainerSpinner;

    private ArrayAdapter timeApapter;
    private Spinner timeSpinner;

    private String ptYear = "";
    private String ptMonth = "";
    private String ptDay = "";
    private String ptTrainer = "";
    private String ptTime = "";

    private ListView ptListView;
    private PTListAdapter ptListAdapter;
    private List<PT> ptList;

    private int count = 0;

    static int userPT = 0;

    //처리를 하는 부분.
    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        yearSpinner = (Spinner) getView().findViewById(R.id.yearSpinner);
        monthSpinner = (Spinner) getView().findViewById(R.id.monthSpinner);
        daySpinner = (Spinner) getView().findViewById(R.id.daySpinner);
        trainerSpinner = (Spinner) getView().findViewById(R.id.trainerSpinner);
        timeSpinner = (Spinner) getView().findViewById(R.id.timeSpinner);

        yearApapter = ArrayAdapter.createFromResource(getActivity(), R.array.year, android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearApapter);
        monthApapter = ArrayAdapter.createFromResource(getActivity(), R.array.month, android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthApapter);
        dayApapter = ArrayAdapter.createFromResource(getActivity(), R.array.day, android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayApapter);
        trainerApapter = ArrayAdapter.createFromResource(getActivity(), R.array.trainer, android.R.layout.simple_spinner_dropdown_item);
        trainerSpinner.setAdapter(trainerApapter);
        timeApapter = ArrayAdapter.createFromResource(getActivity(), R.array.time, android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeApapter);

        //리스트뷰를 어댑터로...
        ptListView = (ListView) getView().findViewById(R.id.ptListView);
        ptList = new ArrayList<PT>();
        ptListAdapter = new PTListAdapter(getContext().getApplicationContext(), ptList , this);
        ptListView.setAdapter(ptListAdapter);



        Button searchButton = (Button) getView().findViewById(R.id.searchButton);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "조회되었습니다", Toast.LENGTH_SHORT).show();
                new BackGroundTask().execute();
            }
        });


        //yearSpinner.setSelection(Integer.parseInt(getDateString().substring(0,4)) - 2018);
        //monthSpinner.setSelection(Integer.parseInt(getDateString().substring(5,7)) - 1);
        //daySpinner.setSelection(Integer.parseInt(getDateString().substring(8,10)) - 1);

        new BackGroundTask().execute();
        new BackGroundTaskForPTnum().execute();
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reservation, container, false);



        return v;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target ;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://kjg123kg.cafe24.com/PTList_SYG.php?ptYear=" +
                        URLEncoder.encode(yearSpinner.getSelectedItem().toString(), "UTF-8") +
                        "&ptMonth=" + URLEncoder.encode(monthSpinner.getSelectedItem().toString(), "UTF-8") +
                        "&ptDay=" + URLEncoder.encode(daySpinner.getSelectedItem().toString(), "UTF-8") +
                        "&ptTrainer=" + URLEncoder.encode(trainerSpinner.getSelectedItem().toString(), "UTF-8") +
                        "&ptTime=" + URLEncoder.encode(timeSpinner.getSelectedItem().toString(), "UTF-8");

                yearSpinner.setSelection(Integer.parseInt(getDateString().substring(0,4)) - 2018);
                monthSpinner.setSelection(Integer.parseInt(getDateString().substring(5,7)) - 1);
                daySpinner.setSelection(Integer.parseInt(getDateString().substring(8,10)) - 1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            try {

                ptList.clear();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                int ptID;
                String ptYear;
                String ptMonth;
                String ptDay;
                String ptTrainer;
                String ptTime;

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    ptID = object.getInt("ptID");
                    ptYear = object.getString("ptYear");
                    ptMonth = object.getString("ptMonth");
                    ptDay = object.getString("ptDay");
                    ptTrainer = object.getString("ptTrainer");
                    ptTime = object.getString("ptTime");

                    PT pt = new PT(ptID, ptYear, ptMonth, ptDay, ptTrainer, ptTime);

                    ptList.add(pt);

                    count++;
                }

                if(count == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReservationFragment.this.getActivity());
                    builder.setMessage("조회된 PT가 없습니다. \n 다른 날짜로 조회해주세요." )
                            .setNegativeButton("확인", null)
                            .create();
                    builder.show();
                }


                ptListAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //데이터베이스에서 user의 pt횟수 받아오는 BackGroundTask
    class BackGroundTaskForPTnum extends AsyncTask<Void, Void, String> {
        String target;
        TextView userRemainPtNumOfReservation;
        @Override
        protected void onPreExecute() {
            try {
                target = "http://kjg123kg.cafe24.com/UserSelect_SYG.php?userID=" + URLEncoder.encode(UserMainActivity.userID, "UTF-8");

                userRemainPtNumOfReservation = (TextView) getView().findViewById((R.id.userRemainPtNumOfReservation));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    userPT = object.getInt("userPT");
                    count++;
                }

                userRemainPtNumOfReservation.setText(userPT + "번");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd일", Locale.KOREA);
        String str_date = df.format(new Date());
        year = str_date.substring(0 , 4);
        Month = str_date.substring(5, 7);
        Day = str_date.substring(9 , 11 );
        return str_date;
    }
}

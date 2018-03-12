package pl.almestinio.socialapp.ui.menuSettingsView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.socialapp.MainActivity;
import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.database.DatabaseUser;
import pl.almestinio.socialapp.http.RestClient;
import pl.almestinio.socialapp.http.user.Users;
import pl.almestinio.socialapp.http.userphoto.UserPhoto;
import pl.almestinio.socialapp.http.userphoto.UsersPic;
import pl.almestinio.socialapp.model.User;
import pl.almestinio.socialapp.ui.friendsView.FriendsActivity;
import pl.almestinio.socialapp.ui.profileView.ProfileActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mesti193 on 3/10/2018.
 */

public class SettingsFragment extends Fragment implements SettingsViewContracts.SettingsView {

    @BindView(R.id.textViewLogout)
    TextView textViewLogout;
    @BindView(R.id.imageViewLogout)
    ImageView imageViewLogout;
//    @BindView(R.id.textViewSettingsMenuFriends)
//    TextView textViewSettingsMenuFriends;

    @BindView(R.id.imageViewMenuSettingsUserPic)
    ImageView imageViewMenuSettingsUserPic;
    @BindView(R.id.textViewMenuSettingsUserName)
    TextView textViewMenuSettingsUserName;
    @BindView(R.id.textViewMenuSettingsUserHint)
    TextView textViewMenuSettingsUserHint;


    private Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(1)
            .cornerRadiusDp(30)
            .oval(false)
            .build();

    private SettingsViewContracts.SettingsViewPresenter settingsViewPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_menu, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        settingsViewPresenter = new SettingsViewPresenter(this);

        settingsViewPresenter.loadProfile(User.getUserId());

//        textViewSettingsMenuFriends.setOnClickListener(v -> settingsViewPresenter.onFriendsTextViewClick());
        textViewLogout.setOnClickListener(v -> settingsViewPresenter.onClickLogoutTextView());
        imageViewLogout.setOnClickListener(v -> settingsViewPresenter.onClickLogoutTextView());
        imageViewMenuSettingsUserPic.setOnClickListener(v -> settingsViewPresenter.onProfileViewClick(User.getUserId()));
        textViewMenuSettingsUserName.setOnClickListener(v -> settingsViewPresenter.onProfileViewClick(User.getUserId()));
        textViewMenuSettingsUserHint.setOnClickListener(v -> settingsViewPresenter.onProfileViewClick(User.getUserId()));

        return view;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        DatabaseUser.updateCategories("");
        getActivity().finish();
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    @Override
    public void startFriendsActivity() {
        startActivity(new Intent(getActivity(), FriendsActivity.class));
    }

    @Override
    public void showProfileData(String userId) {
        try{
            RestClient.getClient().requestUserPhoto(User.getUserId()).enqueue(new Callback<UserPhoto>() {
                @Override
                public void onResponse(Call<UserPhoto> call, Response<UserPhoto> response) {
                    for(UsersPic userPic : response.body().getUsersPic()){
                        if(!userPic.getUserPic().getImage().isEmpty()){
                            Picasso.with(getContext()).load(userPic.getUserPic().getImage()).fit().centerCrop().transform(transformation).placeholder(R.drawable.logo).into(imageViewMenuSettingsUserPic);
                        }
                    }
                }
                @Override
                public void onFailure(Call<UserPhoto> call, Throwable t) {

                }
            });
            RestClient.getClient().requestUser(User.getUserId()).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    String test = response.body().getUsers().get(0).getUser().getName();
                    textViewMenuSettingsUserName.setText(test);
                }
                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                }
            });
        }catch (Exception e){

        }
    }

    @Override
    public void startProfileActivity(String userId) {
        startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra("userid", userId));
    }
}

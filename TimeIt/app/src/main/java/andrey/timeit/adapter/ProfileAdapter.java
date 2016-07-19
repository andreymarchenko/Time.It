package andrey.timeit.adapter;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import andrey.timeit.R;
import andrey.timeit.dialog.AddingTaskDialogFragment;
import andrey.timeit.dialog.ChangeProfileItemDialogFragment;
import andrey.timeit.fragments.UserProfileFragment;
import andrey.timeit.model.ModelProfile;
import andrey.timeit.model.ModelProfileItem;
import andrey.timeit.model.item;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Andrey on 13.07.2016.
 */
public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ModelProfileItem> maindata;

    UserProfileFragment userProfileFragment;

    SQLiteDatabase database;

    ChangeProfileItemDialogFragment changeProfileItemDialogFragment;

    public ProfileAdapter(UserProfileFragment userProfileFragment) {
        this.userProfileFragment = userProfileFragment;
        maindata = new ArrayList<>();
    }

    public void addItem(ModelProfileItem item) {
        maindata.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public ModelProfileItem getItem(int position) {
        return maindata.get(position);
    }

  /*  @Override
    public int getItemViewType(int position) throws IllegalArgumentException {
        ModelProfileItem profileItem = data.get(position);
        if (ModelLogin.class.isInstance(profileItem)) {
            return 0;
        }
        if (ModelName.class.isInstance(profileItem)) {
            return 1;
        }
        if (ModelLifeSphere.class.isInstance(profileItem)) {
            return 2;
        }
        if (ModelYear.class.isInstance(profileItem)) {
            return 3;
        }
        throw new IllegalArgumentException("Unknown element");
    }
    */
/*
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case 0: {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_login, parent, false);
                viewHolder = new LoginViewHolder(v);
                break;
            }
            case 1: {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_name, parent, false);
                viewHolder = new NameViewHolder(v);
                break;
            }
            case 2: {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_live_sphere, parent, false);
                viewHolder = new LifeSphereViewHolder(v);
                break;
            }
            case 3: {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_year, parent, false);
                viewHolder = new YearViewHolder(v);
                break;
            }
            default:
                viewHolder = null;
        }
        return viewHolder;

    } */

/*
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        View v = userProfileFragment.getView();
        if (ModelLogin.class.isInstance(data.get(position))) {
            ModelLogin modelLogin = (ModelLogin) data.get(position);
            LoginViewHolder loginViewHolder = (LoginViewHolder) holder;
            if (modelLogin.getLogin() != null) {
                loginViewHolder.login.setText("marchenko");
            } else {
                loginViewHolder.login.setText("marchenko");
            }
        }
        if (ModelName.class.isInstance(data.get(position))) {
            ModelName modelName = (ModelName) data.get(position);
            NameViewHolder nameViewHolder = (NameViewHolder) holder;
            if (modelName.getName() != null) {
                nameViewHolder.name.setText("Андрей");
            } else {
                nameViewHolder.name.setText("Андрей");
            }
        }
        if (ModelLifeSphere.class.isInstance(data.get(position))) {
            ModelLifeSphere modelLifeSphere = (ModelLifeSphere) data.get(position);
            LifeSphereViewHolder lifeSphereViewHolder = (LifeSphereViewHolder) holder;
            if (modelLifeSphere.getLifeSphere() != null) {
                lifeSphereViewHolder.sphere.setText("Техническое направление");
            } else {
                lifeSphereViewHolder.sphere.setText("Техническое направление");
            }
        }
        if (ModelYear.class.isInstance(data.get(position))) {
            ModelYear modelYear = (ModelYear) data.get(position);
            YearViewHolder yearViewHolder = (YearViewHolder) holder;
            if (modelYear.getYear() != null) {
                yearViewHolder.age.setText("21");
            } else {
                yearViewHolder.age.setText("21");
            }
        }
    }

    */

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_profile_item, parent, false);
        TextView str = (TextView) v.findViewById(R.id.profile_item_data);
        TextView message = (TextView) v.findViewById(R.id.profile_item_message);
        return new ProfileAdapter.ProfileItemViewHolder(v, str, message);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ProfileItemViewHolder profileItemViewHolder = (ProfileItemViewHolder) holder;
        //profileItemViewHolder.data.setText(maindata.get(position).getData());
        final View itemView = profileItemViewHolder.itemView;
        switch (position) {
            case 0: {
                profileItemViewHolder.data.setText("marchenko");
                profileItemViewHolder.message.setText("Логин для идентификации Вас в базе данных");
                break;
            }
            case 1: {
                profileItemViewHolder.data.setText("123456");
                profileItemViewHolder.message.setText("Ваш сложный пароль," +
                        " чтобы никто не мог узнать о том, чем Вы занимаетесь");
                break;
            }
            case 2: {
                profileItemViewHolder.data.setText("Andrey");
                profileItemViewHolder.message.setText("Имя или псевдоним, как Вам удобнее");
                break;
            }
            case 3: {
                profileItemViewHolder.data.setText("It-technology");
                profileItemViewHolder.message.setText("То, чем Вы занимаетесь," +
                        " или, быть может, хотели бы заниматься");
                break;
            }
            case 4: {
                profileItemViewHolder.data.setText("21");
                profileItemViewHolder.message.setText("Ваш возраст, желательно настоящий");
                break;
            }
            case 5: {
                profileItemViewHolder.data.setText("male");
                profileItemViewHolder.message.setText("Ваш пол");
                break;
            }
            case 6: {
                profileItemViewHolder.data.setText("80");
                profileItemViewHolder.message.setText("Ваш вес, ничего страшного," +
                        " если немного приукрасите действительность");
                break;
            }
            case 7: {
                profileItemViewHolder.data.setText("185");
                profileItemViewHolder.message.setText("Ваш рост в сантиметрах," +
                        " поверьте, для нас это важно");
                break;
            }
            case 8: {
                profileItemViewHolder.data.setText("andrey-95nn@yandex.ru");
                profileItemViewHolder.message.setText("Ваша активная почта, чтобы мы могли сообщить" +
                        " Вам о текущем положении дел");
                break;
            }
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            FragmentManager fragmentManager = userProfileFragment.getFragmentManager();

            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0: {
                        ChangeProfileItemDialogFragment changeProfileItemDialogFragment = new ChangeProfileItemDialogFragment();
                        changeProfileItemDialogFragment.setTitle("Логин");
                        changeProfileItemDialogFragment.show(fragmentManager, "changeProfileItemDialogFragment");
                        break;
                    }
                    case 1: {
                        ChangeProfileItemDialogFragment changeProfileItemDialogFragment = new ChangeProfileItemDialogFragment();
                        changeProfileItemDialogFragment.setTitle("Пароль");
                        changeProfileItemDialogFragment.show(fragmentManager, "changeProfileItemDialogFragment");
                        break;
                    }
                    case 2: {
                        ChangeProfileItemDialogFragment changeProfileItemDialogFragment = new ChangeProfileItemDialogFragment();
                        changeProfileItemDialogFragment.setTitle("Имя");
                        changeProfileItemDialogFragment.show(fragmentManager, "changeProfileItemDialogFragment");
                        break;
                    }
                    case 3: {
                        break;
                    }
                    case 4: {
                        ChangeProfileItemDialogFragment changeProfileItemDialogFragment = new ChangeProfileItemDialogFragment();
                        changeProfileItemDialogFragment.setTitle("Возраст");
                        changeProfileItemDialogFragment.show(fragmentManager, "changeProfileItemDialogFragment");
                        break;
                    }
                    case 5: {
                        break;
                    }
                    case 6: {
                        ChangeProfileItemDialogFragment changeProfileItemDialogFragment = new ChangeProfileItemDialogFragment();
                        changeProfileItemDialogFragment.setTitle("Вес");
                        changeProfileItemDialogFragment.show(fragmentManager, "changeProfileItemDialogFragment");
                        break;
                    }
                    case 7: {
                        ChangeProfileItemDialogFragment changeProfileItemDialogFragment = new ChangeProfileItemDialogFragment();
                        changeProfileItemDialogFragment.setTitle("Рост");
                        changeProfileItemDialogFragment.show(fragmentManager, "changeProfileItemDialogFragment");
                        break;
                    }
                    case 8: {
                        ChangeProfileItemDialogFragment changeProfileItemDialogFragment = new ChangeProfileItemDialogFragment();
                        changeProfileItemDialogFragment.setTitle("Email");
                        changeProfileItemDialogFragment.show(fragmentManager, "changeProfileItemDialogFragment");
                        break;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return maindata.size();
    }

    public class ProfileItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView data;
        protected TextView message;

        public ProfileItemViewHolder(View itemView, TextView data, TextView message) {
            super(itemView);
            this.data = data;
            this.message = message;
        }
    }
}

   /* public class LoginViewHolder extends RecyclerView.ViewHolder {
        TextView login;
        TextView loginMessage;

        public LoginViewHolder(View itemView) {
            super(itemView);
            login = (TextView) itemView.findViewById(R.id.input_login);
            loginMessage = (TextView) itemView.findViewById(R.id.input_login_message);
        }
    }

    public class NameViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView nameMessage;

        public NameViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.input_name);
            nameMessage = (TextView) itemView.findViewById(R.id.input_name_message);
        }
    }

    public class LifeSphereViewHolder extends RecyclerView.ViewHolder {
        TextView sphere;
        TextView sphereMessage;

        public LifeSphereViewHolder(View itemView) {
            super(itemView);
            sphere = (TextView) itemView.findViewById(R.id.input_sphere);
            sphereMessage = (TextView) itemView.findViewById(R.id.input_sphere_message);
        }
    }

    public class YearViewHolder extends RecyclerView.ViewHolder {
        TextView age;
        TextView yearMessage;

        public YearViewHolder(View itemView) {
            super(itemView);
            age = (TextView) itemView.findViewById(R.id.input_year);
            yearMessage = (TextView) itemView.findViewById(R.id.input_year_message);
        }
    }
*/





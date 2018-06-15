import Welcome from '../components/Welcome';
import DeviceHistory from '../components/DeviceHistory';
import Login from '../components/Login';
import PersonalInfo from '../components/PersonalInfo';
import DeviceLatest from '../components/DeviceLatest';
import WarningEvent from '../components/WarningEvent';
import NewsList from '../components/NewsList';

export default [
  {
    path: '/',
		component: Login,
		childRoutes: [
			{ path: 'welcome', component: Welcome },
			{ path: 'personalInfo', component: PersonalInfo },
			{ path: 'deviceLatest', component: DeviceLatest },
			{ path: 'deviceHistory', component: DeviceHistory },
			{ path: 'warningEvent', component: WarningEvent },
			{ path: 'newsList', component: NewsList },
		]
  }
]
package go.id.dinkesjatengprov.ilikes.data

import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.MainMenuModel
import go.id.dinkesjatengprov.ilikes.helper.*
import go.id.dinkesjatengprov.ilikes.ui.activity.consultation.ConsultationActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.home.Home2Activity
import go.id.dinkesjatengprov.ilikes.ui.activity.psc.PscActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.telemedicine.TelemedicineActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.treatment.TreatmentActivity

/**
 * MENU DAFTAR BEROBAT
 * */
val menuDaftarBerobat = MainMenuModel(
    title = "Pendaftaran Online",
    icon = R.drawable.img_medicine,
    tag = MENU_DAFTAR_BEROBAT,
    tint = R.color.menu_tint_color_orange,
    page = TreatmentActivity(),
    type = 1
)

/**
 * MENU PEDULI LINDUNGI
 * */
val menuPeduliLindungi = MainMenuModel(
    title = "Satu\nSehat",
    icon = R.drawable.satu_sehat,
    tag = MENU_PEDULI_LINDUNGI,
    tint = R.color.menu_tint_color_white,
    url = "https://satusehat.kemkes.go.id/data/",
    type = 2
)

/**
 * MENU DARURAT 119
 * */
val menuDarurat119 = MainMenuModel(
    title = "Darurat\n119",
    icon = R.drawable.img_psc119,
    tag = MENU_DARURAT_119,
    tint = R.color.menu_tint_color_white,
    page = PscActivity(),
    type = 1
)

val menuConsultation = MainMenuModel(
    title = "Konsultasi\nOnline",
    icon = R.drawable.img_consultation,
    tag = MENU_KONSULTASI,
    tint = R.color.menu_tint_color_yellow,
    page = ConsultationActivity(),
    type = 1
)

val menuHotlineDinkes = MainMenuModel(
    title = "Hotline\nDinkes Prov",
    icon = R.drawable.img_report,
    tag = MENU_PENGADUAN_MASYARAKAT,
    tint = R.color.menu_tint_color_green,
    url = "https://api.whatsapp.com/send?phone=+628112622000",
    type = 3
)

val menuSeeAll = MainMenuModel(
    title = "Lihat\nSemua Fitur",
    icon = R.drawable.img_other,
    tag = MENU_SEMUA_FITUR,
    tint = R.color.menu_tint_color_red,
    page = Home2Activity(),
    type = 1
)

val menuCekLab = MainMenuModel(
    title = "Daftar\nCek Lab",
    icon = R.drawable.img_laboratorium,
    tag = MENU_DAFTAR_CEK_LAB,
    tint = R.color.menu_tint_color_green,
    url = "http://silabkes.labkesjateng.id",
    type = 2
)

val menuOSS = MainMenuModel(
    title = "Perijinan\nUsaha OSS",
    icon = R.drawable.img_oss,
    tag = MENU_PERIJINAN_OSS,
    tint = R.color.menu_tint_color_yellow,
    url = "https://oss.go.id/",
    type = 2
)

val menu5ng = MainMenuModel(
    title = "5NG",
    icon = R.drawable.img_5ng,
    tag = MENU_5NG,
    tint = R.color.menu_tint_color_yellow,
    url = "https://5ng.dinkesjatengprov.go.id/",
    type = 2,
    isLock = true
)

val menuECohort = MainMenuModel(
    title = "eCohort",
    icon = R.drawable.img_ecohort,
    tag = MENU_ECOHORT,
    tint = R.color.menu_tint_color_yellow,
    url = "https://ekohort.kemkes.go.id",
    type = 2,
    isLock = true
)

val menuMPDN = MainMenuModel(
    title = "MPDN",
    icon = R.drawable.img_mpdn,
    tag = MENU_MPDN,
    tint = R.color.menu_tint_color_pink,
    url = "https://mpdn.kemkes.go.id/masuk",
    type = 2,
    isLock = true
)

val menuSITB = MainMenuModel(
    title = "SITB",
    icon = R.drawable.img_sitb,
    tag = MENU_SITB,
    tint = R.color.menu_tint_color_white,
    url = "https://mobile.sitb.id/logon",
    type = 2,
    isLock = true
)

val menuKontakPMI = MainMenuModel(
    title = "Kontak PMI\nJawa Tengah",
    icon = R.drawable.img_pmi,
    tag = MENU_PMI_CONTACT,
    tint = R.color.menu_tint_color_white,
    type = 4
)

val menuPakde = MainMenuModel(
    title = "PAKDE",
    icon = R.drawable.img_pakde,
    tag = MENU_PAKDE,
    tint = R.color.menu_tint_color_white,
    url = "http://pak.dinkesjatengprov.go.id/login",
    type = 2,
    isLock = true
)

val menuPermohonanInformasi = MainMenuModel(
    title = "Permohonan Informasi\nDinkes",
    icon = R.drawable.img_information,
    tag = MENU_INFORMATION_REQUEST,
    tint = R.color.menu_tint_color_yellow,
    url = "https://s.id/Permohonan_Informasi_Dinkes",
    type = 2
)

val menuOpenDataDinkes = MainMenuModel(
    title = "Open Data\nDinkes",
    icon = R.drawable.img_dataset,
    tag = MENU_DATASET_DINKES,
    tint = R.color.menu_tint_color_yellow,
    url = "https://data.jatengprov.go.id/organization/dinas-kesehatan-provinsi-jawa-tengah",
    type = 2
)

val menuBukuSaku = MainMenuModel(
    title = "Buku Saku\nDinkes",
    icon = R.drawable.img_dataset,
    tag = MENU_BUKU_SAKU,
    tint = R.color.menu_tint_color_yellow,
    url = "http://dinkesjatengprov.go.id/v2018/buku-saku-2/",
    type = 2
)

val menuSPMBK = MainMenuModel(
    title = "SPM-BK",
    icon = R.drawable.img_spmbk,
    tag = MENU_SPMBK,
    tint = R.color.menu_tint_color_yellow,
    url = "http://dinkesjatengprov.go.id/v2018/spm-2/",
    type = 2
)

val menuProfilKesehatan = MainMenuModel(
    title = "Profil\nKesehatan",
    icon = R.drawable.img_information,
    tag = MENU_PROFIL_KESEHATAN,
    tint = R.color.menu_tint_color_yellow,
    url = "http://dinkesjatengprov.go.id/v2018/profil-kesehatan-2/",
    type = 2
)

val menuDataRS = MainMenuModel(
    title = "Data RS &\nPuskesmas",
    icon = R.drawable.img_data_rs,
    tag = MENU_DATA_RS,
    tint = R.color.menu_tint_color_yellow,
    url = "http://dinkesjatengprov.go.id/v2018/data-dasar-2/",
    type = 2
)

val menuMTempatTidurRS = MainMenuModel(
    title = "Tempat\nTidur RS",
    icon = R.drawable.img_bed_hospital,
    tag = MENU_TEMPAT_TIDUR_RS,
    tint = R.color.menu_tint_color_yellow,
    url = "https://yankes.kemkes.go.id/app/siranap/",
    type = 2
)

val menuVaccine = MainMenuModel(
    title = "Faskes mampu\nVaksinasi",
    icon = R.drawable.img_vaccine,
    tag = MENU_FASKES_VACCINE,
    tint = R.color.menu_tint_color_yellow,
    url = "https://covid19.go.id/faskesvaksin",
    type = 2
)

val menuStokDarah = MainMenuModel(
    title = "Stok Darah",
    icon = R.drawable.img_pmi,
    tag = MENU_PMI_BLOOD_STOCK,
    tint = R.color.menu_tint_color_white,
    type = 4
)

val menuJadwalDonor = MainMenuModel(
    title = "Jadwal Donor",
    icon = R.drawable.img_pmi,
    tag = MENU_PMI_BLOOD_DONOR,
    tint = R.color.menu_tint_color_white,
    type = 4
)

val menuCorJat = MainMenuModel(
    title = "Corona Jateng",
    icon = R.drawable.img_viruses,
    tag = MENU_CORONA_JATENG,
    tint = R.color.menu_tint_color_white,
    type = 2,
    url = "https://corona.jatengprov.go.id/"
)

val menuLaporgub = MainMenuModel(
    title = "Laporgub",
    icon = R.drawable.ic_laporgub,
    tag = MENU_LAPORGUB,
    tint = R.color.menu_tint_color_yellow,
    type = 2,
    url = "https://laporgub.jatengprov.go.id/"
)

val menuLaporSp4n = MainMenuModel(
    title = "Lapor SP4n",
    icon = R.drawable.ic_sp4n,
    tag = MENU_LAPOR_SP4N,
    tint = R.color.menu_tint_color_yellow,
    type = 2,
    url = "https://lapor.go.id/"
)

val menuStockObat = MainMenuModel(
    title = "Stock Obat /\nBarangDinkes",
    icon = R.drawable.drugs,
    tag = MENU_STOCK_OBAT,
    tint = R.color.menu_tint_color_white,
    type = 2,
    url = "https://logistikfarmasi.dinkesjatengprov.go.id/",
    isLock = true
)

val menuDinkesBox = MainMenuModel(
    title = "DINKES BOX",
    icon = R.drawable.folder,
    tag = MENU_DINKES_BOX,
    tint = R.color.menu_tint_color_white,
    type = 2,
    url = "https://dinkesbox.dinkesjatengprov.go.id:5001/",
    isLock = true
)

val menuKomdatKosmas = MainMenuModel(
    title = "KOMDAT KESMAS",
    icon = R.drawable.img_sitb,
    tag = MENU_KOMDAT_KOSMAS,
    tint = R.color.menu_tint_color_white,
    type = 2,
    url = "https://komdatkesmas.kemkes.go.id/login",
    isLock = true
)

val menuSSO = MainMenuModel(
    title = "SSO",
    icon = R.drawable.img_sso,
    tag = MENU_SSO,
    tint = R.color.menu_tint_color_yellow,
    type = 2,
    url = "https://sso.jatengprov.go.id",
    isLock = true
)

val menuForm = MainMenuModel(
    title = "Survei Kepuasan\nMasyarakat",
    icon = R.drawable.form,
    tag = MENU_SURVEY,
    tint = R.color.menu_tint_color_yellow,
    type = 2,
    url = "https://ee.kobotoolbox.org/single/1ae846c97015337948c244e289b6b132",
)

val menuKtki = MainMenuModel(
    title = "e-STR KemKes",
    icon = R.drawable.estr_kemenkes,
    tag = MENU_KTKI,
    tint = R.color.menu_tint_color_white,
    url = "https://ktki.kemkes.go.id/admin/login",
    type = 2,
    isLock = true
)

val menuSkdr = MainMenuModel(
    title = "SKDR",
    icon = R.drawable.skdr,
    tag = MENU_SKDR,
    tint = R.color.menu_tint_color_white,
    url = "https://skdr.surveilans.org/auth",
    type = 2,
    isLock = true
)

val menuTossTBC = MainMenuModel(
    title = "TOSS TBC",
    icon = R.drawable.img_toss_tbc,
    tag = MENU_TOSS_TBC,
    tint = R.color.menu_tint_color_white,
    url = "id.or.tbindonesia.dashboard",
    type = 3,
    isLock = true
)

val menuTelemedicine = MainMenuModel(
    title = "Telemedisin",
    icon = R.drawable.ic_telemedicine,
    tag = MENU_TELEMEDICINE,
    tint = R.color.menu_tint_color_white,
    type = 1,
    page = TelemedicineActivity(),
)

val menuPetaJateng = MainMenuModel(
    title = "Peta Sebaran Jateng",
    icon = R.drawable.ic_peta_jateng,
    tag = MENU_PETA_JATENG,
    tint = R.color.menu_tint_color_white,
    type = 2,
    url = "https://dinkes.jatengprov.go.id/statplanet/"
)

val menuEppbgm = MainMenuModel(
    title = "EPPGBM",
    icon = R.drawable.ic_eppgbm,
    tag = MENU_EPPBGM,
    tint = R.color.menu_tint_color_white,
    type = 2,
    isLock = true,
    url = "https://sigiziterpadu.kemkes.go.id/login_sisfo/"
)

val menuInfoAlkes = MainMenuModel(
    title = "Info Alkes",
    icon = R.drawable.ic_info_alkes,
    tag = MENU_INFO_ALKES,
    tint = R.color.menu_tint_color_white,
    type = 2,
    url = "https://infoalkes.kemkes.go.id/"
)

val menuMyDinkes = arrayListOf(
    menuStockObat,
    menuDinkesBox,
    menuSSO,
    menuKomdatKosmas,
    menu5ng,
    menuECohort,
    menuMPDN,
    menuPakde,
    menuSITB,
    menuKtki,
    menuSkdr,
    menuTossTBC,
    menuEppbgm
)

/**
 * Daftar menu utama pada dashboard / home aplikasi
 *
 * */
val menuDashboard1 = arrayListOf(
    menuDaftarBerobat,
//    menuPeduliLindungi,
    menuForm,
    menuDarurat119,
    menuConsultation,
    menuHotlineDinkes,
    menuSeeAll,
)

/**
 * Daftar menu sekunder / daftar menu layanan kesehatan
 * pada halaman semua menu aplikasi
 *
 * */
val menuDashboard2 = arrayListOf(
    menuDaftarBerobat,
    menuTelemedicine,
    menuPeduliLindungi,
    menuDarurat119,
    menuCekLab,
    menuConsultation,
    menuOSS,
    menuKontakPMI,
    menuPermohonanInformasi,
)

/**
 * Daftar menu sekunder / daftar menu informasi
 * pada halaman semua menu aplikasi
 *
 * */
val menuInformation = arrayListOf(
    menuOpenDataDinkes,
    menuSPMBK,
    menuProfilKesehatan,
    menuDataRS,
    menuBukuSaku,
    menuMTempatTidurRS,
    menuVaccine,
    menuStokDarah,
    menuJadwalDonor,
    menuCorJat,
    menuPetaJateng,
    menuInfoAlkes
)

val menuLayananPengaduan = arrayListOf(
    menuLaporgub,
    menuLaporSp4n,
    menuHotlineDinkes
)


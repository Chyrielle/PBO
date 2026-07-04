import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean aplikasiJalan = true;
        // sesi login
        while (aplikasiJalan) {
            System.out.println("=== LOGIN ===");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            boolean loginBerhasil = User.login(username, password);

            if (!loginBerhasil) {
                System.out.println("Login gagal, username/password salah.\n");
                continue;
            }

            System.out.println("Login berhasil!\n");
            // sesi logout
            boolean sudahLogout = false;
            while (!sudahLogout) {
                // menu nya loh ya
                System.out.println("=== MENU ===");
                System.out.println("1. Lihat daftar tiket");
                System.out.println("2. Logout");
                System.out.println("3. Beli tiket");
                System.out.print("Pilih: ");
                String pilihan = scanner.nextLine();

                switch (pilihan) {
                    case "1":
                        Tiket.tampilkanSemua();
                        break;
                    case "2":
                        System.out.println("Logout berhasil.\n");
                        sudahLogout = true;
                        break;
                    case "3":
                        System.out.print("ID Tiket: ");
                        int idTiket = Integer.parseInt(scanner.nextLine());
                        System.out.print("Jumlah beli: ");
                        int jumlahBeli = Integer.parseInt(scanner.nextLine());

                        Transaksi.Status status = Transaksi.beli(idTiket, jumlahBeli);

                        if (status == Transaksi.Status.SUKSES) {
                            System.out.println("Transaksi berhasil!");
                        } else if (status == Transaksi.Status.STOK_TIDAK_CUKUP) {
                            System.out.println("STOK TIDAK CUKUP! Jumlah beli melebihi stok tersedia.");
                        } else {
                            System.out.println("Transaksi gagal, coba lagi.");
                        }
                        break;
                    default:
                        System.out.println("Pilihan gak valid.");
                }
            }
        }
    }
}
package practicumopdracht.data;

import practicumopdracht.models.Artist;

import java.io.*;
import java.util.ArrayList;

public class BinaryArtistDAO extends ArtistDAO {
    private static final String FILENAME = "src/artist.bin";

    public BinaryArtistDAO(){
        File file = new File(FILENAME);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        objects = new ArrayList<>();
    }

    @Override
    public boolean save() {
        File file = new File(FILENAME);
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        ) {
            dataOutputStream.writeInt(objects.size());
            for (Artist artist : objects) {
                System.out.println("AS: " + artist.getCurrentFileName());
                dataOutputStream.writeUTF(artist.getName());
                dataOutputStream.writeUTF(artist.getLabel());
                dataOutputStream.writeBoolean(artist.isFavorited());
                dataOutputStream.writeUTF(artist.getCurrentFileName());

            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return super.save();
    }

    @Override
    public boolean load() {
        objects = new ArrayList<Artist>();

        File file = new File(FILENAME);
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        ) {
            int objectAmount = dataInputStream.readInt();

            for (int i = 0; i < objectAmount; i++) {
                String name = dataInputStream.readUTF();
                String label = dataInputStream.readUTF();
                Boolean favorite = dataInputStream.readBoolean();
                String imagePath = dataInputStream.readUTF();

                Artist loadedArtist = new Artist(name,label,favorite,imagePath);
                System.out.println(loadedArtist);
                objects.add(loadedArtist);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return super.load();
    }
}

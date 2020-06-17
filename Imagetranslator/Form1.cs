using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Tesseract;
using DarrenLee.Translator;
using AForge.Imaging.Filters;
using System.Drawing.Imaging;

namespace Imagetranslator
{
    public partial class Form1 : Form
    {
        private System.Drawing.Bitmap sourceImage;
        private System.Drawing.Bitmap filteredImage;
        public Form1()
        {
            InitializeComponent();
        }

        private void btnAbrirImagen_Click(object sender, EventArgs e)
        {


            try
            {
                // show file open dialog
                if (openFileDialog1.ShowDialog() == DialogResult.OK)
                {
                    // load image
                    sourceImage = (Bitmap)Bitmap.FromFile(openFileDialog1.FileName);

                    // check pixel format
                    if ((sourceImage.PixelFormat == PixelFormat.Format16bppGrayScale) ||
                         (Bitmap.GetPixelFormatSize(sourceImage.PixelFormat) > 32))
                    {
                        MessageBox.Show("The demo application supports only color images.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        // free image
                        sourceImage.Dispose();
                        sourceImage = null;
                    }
                    else
                    {
                        // make sure the image has 24 bpp format
                        if (sourceImage.PixelFormat != PixelFormat.Format24bppRgb)
                        {
                            Bitmap temp = AForge.Imaging.Image.Clone(sourceImage, PixelFormat.Format24bppRgb);
                            sourceImage.Dispose();
                            sourceImage = temp;
                        }
                    }

                    // display image
                    pictureBox1.Image = sourceImage;
                }
            }
            catch
            {
                MessageBox.Show("Failed loading the image", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }




            Bitmap originalImage = sourceImage;

            sourceImage = Grayscale.CommonAlgorithms.RMY.Apply(sourceImage);

            ApplyFilter(new BradleyLocalThresholding());

            sourceImage.Dispose();
            sourceImage = originalImage;



            DeteccionTraduccion();
           
        }
        private void ApplyFilter(IFilter filter)
        {
            // apply filter
            filteredImage = filter.Apply(sourceImage);
            // display filtered image
            pictureBox2.Image = filteredImage;
        }
        public void DeteccionTraduccion()
        {
            //deteccion de letras
            var tesseract = new TesseractEngine(@"./tessdata", "eng", EngineMode.TesseractAndCube);
            var page = tesseract.Process(filteredImage);
            string let;
            let = page.GetText();
            rtxtLetras.Text = let;
            //TRADUCTOR
            string textotraducido = Translator.Translate(let, "en", "es");
            richTextBox1.Text = textotraducido;
        }
    }
}


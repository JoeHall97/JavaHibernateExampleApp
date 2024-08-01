import json 
import random
import os
import xml.etree.ElementTree as ET
import datetime

def read_suburb_data(filename: str) -> dict[str,list[str]]:
    with open(filename, encoding="utf8") as f:
        data = json.load(f)
        return data

def create_xml_output(location: str, suburbs: list[str]) -> ET.ElementTree:
    root = ET.Element("Location", name = location)
    start_date = datetime.datetime.strptime('1960-01-01', '%Y-%m-%d')
    end_date = datetime.datetime.now()

    for s in suburbs:
        suburb_xml = ET.SubElement(root, "Suburb", name = s)
        random_temp = round(random.uniform(-30.0, 60.0), 1)
        random_date = start_date + (end_date - start_date) * random.random()
        ET.SubElement(suburb_xml, "Temperature").text = str(random_temp)
        ET.SubElement(suburb_xml, "Date").text = str(random_date.date())
        
    return ET.ElementTree(root)

if __name__ == "__main__":
    suburb_data = read_suburb_data(os.path.join("..", "..", "data", "suburbs.json")) 
    print(os.getcwd())

    for city, suburbs in suburb_data.items():
        xml = create_xml_output(city, suburbs)
        folder = os.path.join("..", "..", "data", "output", city)
        if not os.path.exists(folder):
            os.makedirs(folder)
        xml.write(os.path.join(folder, f'output_{datetime.date.today()}.xml'), encoding="utf8")